package br.com.calcred.api.dto.funcao.simulacao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TipoPrimeiroVencimento {

    ANIVERSARIO("Aniversario"),
    CARENCIA("Carencia"),
    INFORMADO("Informado");

    String valor;
}