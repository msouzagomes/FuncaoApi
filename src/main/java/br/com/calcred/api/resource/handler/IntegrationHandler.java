package br.com.calcred.api.resource.handler;

import br.com.calcred.api.helper.MessageHelper;
import br.com.calcred.api.integration.exception.IntegrationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

//import static br.com.calcred.ep.enumeration.ErrorCodeEnum.ERROR_INTEGRATION_INTERNAL_SERVER_ERROR;


@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class IntegrationHandler {

    private final MessageHelper messageHelper;


 /*   @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<?> integrationHandler(ResponseStatusException i){
        switch (i.getStatus().value()) {
            case 400:
                log.debug(i.getClass() + ": " +  i.getMessage(), i);
                return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
            case 404:
                log.debug(i.getClass() + ": " +  i.getMessage(), i);
                return new ResponseEntity<>(i.getMessage(), i.getStatus());
            default:
                return new ResponseEntity<>(messageHelper.get(ERROR_INTEGRATION_INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
    @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<String> integrationHandler(ResponseStatusException i){
         log.debug(i.getClass() + ": " +  i.getMessage(), i);
         return new ResponseEntity<>("Exception handler tratando resposta do fallback -> " + i.getMessage(), i.getStatus());
    }

    @ExceptionHandler(value = IntegrationException.class)
    public ResponseEntity<String> integrationHandler(IntegrationException i){
        log.debug(i.getClass() + ": " +  i.getMessage(), i);
        return new ResponseEntity<>("Exception handler tratando resposta do fallback -> " + i.getMessage(), i.getStatus());
    }


  /*  @ExceptionHandler(value = HystrixRuntimeException.class)
    public ResponseEntity<?> integrationHystrixHandler(HystrixRuntimeException i){
        log.debug(i.getClass() + ": " +  i.getMessage(), i);
        return new ResponseEntity<>(messageHelper.get(ERROR_INTEGRATION_INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

}
