package br.com.calcred.api.integration.funcao.config;

import static br.com.calcred.api.exception.ErrorCodeEnum.ERRO_INTEGRACAO_FUNCAO;
import static br.com.calcred.api.integration.funcao.dto.erro.CodigoError.CLIENTE_SEM_PROPOSTAS;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Optional.ofNullable;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.calcred.api.exception.BusinessErrorException;
import br.com.calcred.api.exception.InternalErrorException;
import br.com.calcred.api.helper.MessageHelper;
import br.com.calcred.api.integration.config.CustomRetryer;
import br.com.calcred.api.integration.funcao.dto.erro.FuncaoErrorBody;
import br.com.calcred.api.integration.funcao.dto.erro.FuncaoErrorBody.Erro;
import br.com.calcred.api.integration.funcao.dto.erro.FuncaoStatusBody;
import br.com.calcred.api.integration.funcao.dto.erro.FuncaoStatusBodyResponse;
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
public class FuncaoFeignConfig {

    private final MessageHelper messageHelper;
    private final ObjectMapper objectMapper;

    @Bean
    ErrorDecoder errorDecoder() {
        return (s, response) -> {

            final FuncaoStatusBodyResponse responseStatus = getFuncaoStatusBodyResponse(response);
            final HttpStatus status = HttpStatus.valueOf(response.status());

            logHttpError(response, status);

            final String errorMessage = messageHelper.get(ERRO_INTEGRACAO_FUNCAO);

            return Match(response).of(
                Case($(res -> status == BAD_REQUEST &&
                    ofNullable(responseStatus).
                        map(FuncaoStatusBodyResponse::getStatusBody)
                        .map(FuncaoStatusBody::getFuncaoErrorBody)
                        .map(FuncaoErrorBody::getErros)
                        .map(erros -> erros.get(0))
                        .map(Erro::getCodigo)
                        .orElse(null) == CLIENTE_SEM_PROPOSTAS), new BusinessErrorException(NOT_FOUND)),
                Case($(res -> HttpStatus.valueOf(res.status()).is5xxServerError() || status == UNAUTHORIZED),
                    new RetryableException(
                        status.value(),
                        errorMessage,
                        response.request().httpMethod(),
                        null,
                        response.request())),
                Case($(), new InternalErrorException(errorMessage)));
        };
    }

    private FuncaoStatusBodyResponse getFuncaoStatusBodyResponse(final Response response) {
        try {
            return objectMapper.readValue(response.body().toString(), FuncaoStatusBodyResponse.class);
        } catch (final JsonProcessingException e) {
            log.warn("Erro ao converter FuncaoStatusBodyResponse.");
            return null;
        }
    }

    private static void logHttpError(final Response response, final HttpStatus responseStatus) {
        ofNullable(responseStatus).ifPresent(status -> {
            final Request request = response.request();
            final String responseBody = Try.withResources(() -> response.body().asReader(UTF_8))
                .of(IOUtils::toString)
                .getOrElse(EMPTY);
            if (BAD_REQUEST.equals(status)) {
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
    Encoder jacksonEncoder(final PageJacksonModule pageModule, final ObjectMapper mapper) {
        mapper.registerModule(pageModule);
        return new JacksonEncoder(mapper);
    }

    @Bean
    Decoder jacksonDecoder(final PageJacksonModule pageModule, final ObjectMapper mapper) {
        mapper.registerModule(pageModule);
        return new JacksonDecoder(mapper);
    }

    @Bean
    Retryer retryer(@Value("${feign.client.retryer.period:1000}") final long period,
        @Value("${feign.client.retryer.max-period:5000}") final long maxPeriod,
        @Value("${feign.client.retryer.max-attempts:3}") final int maxAttempts) {
        return new CustomRetryer(period, maxPeriod, maxAttempts);
    }
}