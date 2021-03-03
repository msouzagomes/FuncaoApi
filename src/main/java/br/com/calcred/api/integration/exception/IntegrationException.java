package br.com.calcred.api.integration.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IntegrationException extends RuntimeException {

    private HttpStatus status;

    public IntegrationException(HttpStatus status, String message, Throwable throwable) {
        super(message, throwable);
        this.status = status;
    }

    public IntegrationException(HttpStatus status, Throwable throwable) {
        super(throwable);
        this.status = status;
    }

    public IntegrationException(HttpStatus status) {
        super();
        this.status = status;
    }

    public IntegrationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}