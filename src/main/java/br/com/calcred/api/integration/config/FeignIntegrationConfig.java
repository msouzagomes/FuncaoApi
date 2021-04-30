package br.com.calcred.api.integration.config;

import static br.com.calcred.api.exception.ErrorCodeEnum.ERROR_ACCOUNT_INTEGRATION_DECODER;
import static feign.Request.HttpMethod.GET;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Optional.ofNullable;
import static org.apache.logging.log4j.util.Strings.EMPTY;

import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.calcred.api.helper.MessageHelper;
import br.com.calcred.api.integration.exception.IntegrationException;
import feign.Request;
import feign.Response;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FeignIntegrationConfig {

    private final MessageHelper messageHelper;

    @Bean
    ErrorDecoder errorDecoder() {
        return (s, response) -> {
            Optional<HttpStatus> responseStatus = Optional.of(HttpStatus.valueOf(response.status()));
            logHttpError(response, responseStatus);
            final Optional<String> errorMessage = Optional.of(messageHelper.get(ERROR_ACCOUNT_INTEGRATION_DECODER));
            return Match(response).of(
                Case($(res -> GET.equals(res.request().httpMethod()) && HttpStatus.valueOf(res.status())
                        .is5xxServerError()),
                    new RetryableException(
                        responseStatus.map(HttpStatus::value).orElseThrow(),
                        errorMessage.orElse(response.reason()),
                        response.request().httpMethod(),
                        null,
                        response.request()
                    )
                ),
                Case($(),
                    new IntegrationException(errorMessage.orElse(response.reason()), responseStatus.orElseThrow()))
            );
        };
    }

    private static void logHttpError(final Response response, final Optional<HttpStatus> responseStatus) {
        responseStatus.ifPresent(status -> {
            Request request = response.request();
            String responseBody = Try.withResources(() -> response.body().asReader(UTF_8))
                .of(IOUtils::toString)
                .getOrElse(EMPTY);
            if (HttpStatus.BAD_REQUEST.equals(status)) {
                log.warn(buildLogFullMessage(),
                    request.httpMethod() + " " + request.url(),
                    new String(ofNullable(request.body()).orElse(EMPTY.getBytes()), UTF_8),
                    responseBody);
            } else if (status.is5xxServerError()) {
                log.error(buildLogFullMessage(),
                    request.httpMethod() + " " + request.url(),
                    new String(ofNullable(request.body()).orElse(EMPTY.getBytes()), UTF_8),
                    responseBody);
            }
        });
    }

    private static String buildLogFullMessage() {
        return lineSeparator() +
            "RequestUrl: {}" + lineSeparator() +
            "RequestBody: {}" + lineSeparator() +
            "Response: {}" + lineSeparator();
    }

    @Bean
    public PageJacksonModule pageJacksonModule() {
        return new PageJacksonModule();
    }

    @Bean
    Encoder jacksonEncoder(PageJacksonModule pageModule, ObjectMapper mapper) {
        mapper.registerModule(pageModule);
        return new JacksonEncoder(mapper);
    }

    @Bean
    Decoder jacksonDecoder(PageJacksonModule pageModule, ObjectMapper mapper) {
        mapper.registerModule(pageModule);
        return new JacksonDecoder(mapper);
    }

    @Bean
    Retryer retryer(@Value("${feign.client.retryer.period:1000}") long period,
        @Value("${feign.client.retryer.max-period:5000}") long maxPeriod,
        @Value("${feign.client.retryer.max-attempts:3}") int maxAttempts) {
        return new CustomRetryer(period, maxPeriod, maxAttempts);
    }
}