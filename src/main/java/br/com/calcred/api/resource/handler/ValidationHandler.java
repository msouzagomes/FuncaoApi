package br.com.calcred.api.resource.handler;

import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.calcred.api.dto.ErrorMessageDTO;
import br.com.calcred.api.exception.BusinessErrorException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ValidationHandler {

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    void exceptionHandler(final ValidationException e) {
        throw new BusinessErrorException(BAD_REQUEST, e.getMessage(), e);
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    ErrorMessageDTO exceptionHandler(final ConstraintViolationException e, final HttpServletRequest httpServletRequest) {
        return ErrorMessageDTO.builder()
            .message(e.getConstraintViolations()
                .parallelStream()
                .map(objectError -> format("{0}: {1}", objectError.getPropertyPath().toString().split("\\.")[1],
                    (objectError).getMessage()))
                .sorted()
                .collect(Collectors.toList()))
            .error(e.getClass().getSimpleName())
            .path(httpServletRequest.getRequestURI())
            .build();
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ErrorMessageDTO handleValidationExceptions(final MethodArgumentNotValidException e,
        final HttpServletRequest httpServletRequest) {
        return ErrorMessageDTO.builder()
            .message(e.getBindingResult()
                .getAllErrors()
                .parallelStream()
                .map(objectError -> format("{0}: {1}", ((FieldError) objectError).getField(),
                    (objectError).getDefaultMessage()))
                .sorted()
                .collect(Collectors.toList()))
            .error(e.getClass().getSimpleName())
            .path(httpServletRequest.getRequestURI())
            .build();
    }
}
