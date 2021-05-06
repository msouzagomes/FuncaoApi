package br.com.calcred.api.integration.funcao.dto.erro;

import static java.util.Arrays.stream;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CodigoError {

    CARENCIA_SIMULACAO_INVALIDA(90599999),
    CLIENTE_SEM_PROPOSTAS(112970030);

    Integer valor;

    @JsonCreator
    public static CodigoError fromString(final Integer integer) {

        return stream(CodigoError.values())
            .filter(codigoError -> codigoError.getValor().equals(integer))
            .findFirst()
            .orElse(null);
    }
}