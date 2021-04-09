package br.com.calcred.api.integration.funcao.dto.proposta;

import static java.util.Arrays.stream;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SituacaoEsteira {

    ANDAMENTO("Andamento"),
    APROVADA("Aprovada"),
    CANCELADA("Cancelada"),
    INTEGRADA("Integrada"),
    NAO_DEFINIDO("NaoDefinido"),
    PENDENTE("Pendente"),
    REANALISE("Reanalise"),
    REPROVADA("Reprovada");

    String valor;

    @JsonCreator
    public static SituacaoEsteira fromString(String string) {

        return stream(SituacaoEsteira.values())
            .filter(situacaoEsteira -> situacaoEsteira.getValor().equalsIgnoreCase(string))
            .findFirst()
            .orElse(null);
    }
}