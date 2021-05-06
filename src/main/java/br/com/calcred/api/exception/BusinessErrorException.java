package br.com.calcred.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BusinessErrorException extends ResponseStatusException {

    public BusinessErrorException(final HttpStatus status) {
        super(status);
    }

    public BusinessErrorException(final HttpStatus status, final String reason) {
        super(status, reason);
    }

    public BusinessErrorException(final HttpStatus status, final String reason, final Throwable cause) {
        super(status, reason, cause);
    }
}
