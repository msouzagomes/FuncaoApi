package br.com.calcred.api.service;

import autbank.ArrayOfTOTipoRenda;
import autbank.ListarTipoRenda;
import autbank.ListarTipoRendaResponse;
import autbank.TOListaTipoRenda;
import br.com.calcred.api.integration.autbank.AutBankClient;
import br.com.calcred.api.integration.exception.IntegrationException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@Service
public class AutBankService {

    private static int contadorRetry = 0;

    private final AutBankClient autBankClient;

    @Retry(name = "AutBankClient", fallbackMethod = "fallbackRetry")
    @CircuitBreaker(name = "AutBankClient", fallbackMethod = "fallbackCircuitBreacker")
    public ListarTipoRendaResponse listarTipoRenda(@RequestBody ListarTipoRenda listarTipoRenda){
        contadorRetry ++;
        log.debug("Contando o número de Retentativas (Retry's): " + contadorRetry);
        return autBankClient.listarTipoRenda(listarTipoRenda);
    }

    public ListarTipoRendaResponse fallbackRetry(@RequestBody ListarTipoRenda listarTipoRenda, Exception cause)  throws ResponseStatusException {
        log.debug("> fallbackRetry : do Resilience4j");
        if (cause instanceof IntegrationException) {
            log.debug(cause.getMessage());
            log.debug(String.valueOf(cause.getCause()));
            log.debug(String.valueOf(((IntegrationException) cause).getStatus()));
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ListarTipoRendaResponse fallbackCircuitBreacker(@RequestBody ListarTipoRenda listarTipoRenda, CallNotPermittedException cause)  throws ResponseStatusException {
        log.debug("> fallbackCircuitBreacker : do Resilience4j");
        if (cause instanceof RuntimeException) {
            log.debug(cause.getMessage());
            log.debug(String.valueOf(cause.getCause()));
        }
        ListarTipoRendaResponse mockResponse = new ListarTipoRendaResponse();
        TOListaTipoRenda listaTipoRenda = new TOListaTipoRenda();
        listaTipoRenda.setQtdTipoRenda(0);
        ArrayOfTOTipoRenda arrayOfTOTipoRenda = new ArrayOfTOTipoRenda();
        listaTipoRenda.setTipoRenda(arrayOfTOTipoRenda);
        mockResponse.setListarTipoRendaReturn(listaTipoRenda);
        return mockResponse;
    }



}
