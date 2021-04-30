package br.com.calcred.api.builder;

import static java.util.Optional.ofNullable;

import br.com.calcred.api.dto.funcao.proposta.Proposta;
import br.com.calcred.api.integration.funcao.dto.proposta.Proposta.Esteira;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropostaBuilder {

    public static Proposta buildProposta(br.com.calcred.api.integration.funcao.dto.proposta.Proposta proposta) {

        return ofNullable(proposta)
            .map(p -> Proposta.builder()
                .numeroProposta(p.getNumeroProposta())
                .situacaoEsteira(ofNullable(p.getEsteira())
                    .map(Esteira::getSituacaoEsteira)
                    .orElse(null))
                .build())
            .orElse(null);
    }
}
