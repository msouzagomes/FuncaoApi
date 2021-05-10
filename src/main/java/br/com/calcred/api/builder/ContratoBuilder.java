package br.com.calcred.api.builder;

import static java.util.Optional.ofNullable;

import br.com.calcred.api.dto.funcao.contratos.Contrato;
import br.com.calcred.api.integration.funcao.dto.operacao.Operacao;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContratoBuilder {

    public static Contrato buildContrato(final Operacao operacao) {

        return ofNullable(operacao)
            .map(o -> Contrato.builder()
                .numeroContrato(o.getNumeroOperacao())
                .quantidadeParcelas(o.getQuantidadeParcelas())
                .valorCredito(o.getValorCredito())
                .valorParcela(o.getValorParcela())
                .situacaoOperacao(o.getSituacaoOperacao())
                .dataLiquidacao(o.getDataLiquidacao())
                .build())
            .orElse(null);
    }
}