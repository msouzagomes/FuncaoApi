package br.com.calcred.api.validator;

import static br.com.calcred.api.exception.ErrorCodeEnum.CPF_INVALIDO;
import static feign.Util.isBlank;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.calcred.api.exception.BusinessErrorException;
import br.com.calcred.api.helper.MessageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CpfValidator {

    private final MessageHelper messageHelper;

    public void validar(final String cpf) {

        if (isBlank(cpf) || isFalse(new CPFValidator().invalidMessagesFor(cpf).isEmpty())) {
            log.warn("CPF inválido.");
            throw new BusinessErrorException(HttpStatus.BAD_REQUEST, messageHelper.get(CPF_INVALIDO));
        }
    }
}
