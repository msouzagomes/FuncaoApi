package br.com.calcred.api.integration.funcao.dto.erro;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Codigo {

    ERRO("Erro"),
    SUCESSO("Sucesso");

    @JsonValue
    String valor;
}