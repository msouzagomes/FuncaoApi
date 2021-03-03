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

import br.com.calcred.api.helper.MessageHelper;
import br.com.calcred.api.integration.exception.IntegrationException;
import brave.baggage.BaggageField;
import feign.Request;
import feign.RequestInterceptor;
import feign.Response;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.jaxb.JAXBContextFactory;
import feign.soap.SOAPDecoder;
import feign.soap.SOAPEncoder;
import io.vavr.control.Try;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Slf4j
@RequiredArgsConstructor
public class FeignIntegrationSoapConfig {

    private final MessageHelper messageHelper;

    @Bean
    public Encoder getSoapEncoder(){
        JAXBContextFactory jaxbContextFactory =
                new JAXBContextFactory.Builder().withMarshallerJAXBEncoding("UTF-8").build();
        return new SOAPEncoder.Builder()
                .withJAXBContextFactory(jaxbContextFactory)
                .withCharsetEncoding(UTF_8)
                .build();
    }

    @Bean
    public Decoder getSoapDecoder(){
        return new SOAPDecoder.Builder()
                .withJAXBContextFactory(new JAXBContextFactory.Builder().build())
                .useFirstChild()
                .build();
    }

    @Bean
    RequestInterceptor remoteFeignInterceptor(@Value("${api.egress.consumer-key}") String consumerKey) {
        return requestTemplate -> requestTemplate
            .headers(Map.of(
                "SOAPAction", Collections.singletonList("\"\""),
                "Authorization", Collections.singletonList("Basic c3VwZXJ2aXNvcjpzdXBlcnZpc29y"),
                "x-api-key", Collections.singletonList(consumerKey)
            ));
    }

    @Bean
    ErrorDecoder errorDecoder() {
        return (s, response) -> {
            Optional<HttpStatus> responseStatus = Optional.of(HttpStatus.valueOf(response.status()));
            logHttpError(response, responseStatus);
            final Optional<String> errorMessage = Optional.of(messageHelper.get(ERROR_ACCOUNT_INTEGRATION_DECODER));
            return Match(response).of(
                    Case($(res -> GET.equals(res.request().httpMethod()) && HttpStatus.valueOf(res.status()).is5xxServerError()),
                            new RetryableException(
                                    responseStatus.map(HttpStatus::value).orElseThrow(),
                                    errorMessage.orElse(response.reason()),
                                    response.request().httpMethod(),
                                    null,
                                    response.request()
                            )
                    ),
                    Case($(), new IntegrationException(errorMessage.orElse(response.reason()), responseStatus.orElseThrow()))
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

    private static String buildLogFullMessage(){
        return lineSeparator() +
                "RequestUrl: {}" + lineSeparator() +
                "RequestBody: {}" + lineSeparator() +
                "Response: {}" + lineSeparator();
    }

}