package br.com.calcred.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {

    ERROR_AUTENTICACAO_FUNCAO("error.autenticacao.funcao"),
    ERROR_ACCOUNT_INTEGRATION_DECODER("error.account.integration.decoder"),
    ERROR_CPF_INVALID("error.cpf.invalid");

    private final String messageKey;
}
