package br.com.calcred.api.integration.funcao.dto.operacao;

import static java.util.Arrays.stream;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SituacaoOperacao {

    ATIVO(0),
    LIQUIDADO(1);

    Integer valor;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SituacaoOperacao fromInteger(final Integer valor) {

        return stream(SituacaoOperacao.values())
            .filter(situacaoOperacao -> situacaoOperacao.getValor().equals(valor))
            .findFirst()
            .orElse(null);
    }
}