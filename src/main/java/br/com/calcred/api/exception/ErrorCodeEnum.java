package br.com.calcred.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {

    CPF_INVALIDO("cpf.invalido"),
    ERRO_AUTENTICAR_FUNCAO("erro.autenticacao.funcao"),
    ERRO_INTEGRACAO_FUNCAO("erro.integracao.funcao");

    private final String messageKey;
}
