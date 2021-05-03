package br.com.calcred.api.builder;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import br.com.calcred.api.dto.funcao.simulacao.Simulacao.Parcela;
import br.com.calcred.api.integration.funcao.dto.simulacao.Despesas;
import br.com.calcred.api.integration.funcao.dto.simulacao.Despesas.Despesa;
import br.com.calcred.api.integration.funcao.dto.simulacao.Simulacao;
import br.com.calcred.api.integration.funcao.dto.simulacao.Simulacao.Parcelas;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimulacaoMapper {

    public static br.com.calcred.api.dto.funcao.simulacao.Simulacao buildSimulacao(final Simulacao simulacao) {

        return ofNullable(simulacao)
            .map(s -> br.com.calcred.api.dto.funcao.simulacao.Simulacao.builder()
                .parcelas(buildParcelas(s.getParcelas()))
                .taxaCetMensal(s.getTaxaCetAm())
                .impostosOperacao(s.getValorIOF())
                .jurosMensal(s.getTaxaClAm())
                .taxaCetAnual(s.getTaxaCetAa())
                .quantidadeParcelas(s.getQuantidadeParcelas())
                .tarifaCredito(buildTarifaCredito(s.getDespesas()))
                .valorSolicitado(s.getValorFinanciado())
                .valorFinanciado(s.getValorBruto())
                .valorParcelas(s.getValorParcela())
                .build())
            .orElse(null);
    }

    private static List<Parcela> buildParcelas(final Parcelas parcelas) {

        final AtomicInteger indice = new AtomicInteger(1);

        return ofNullable(parcelas)
            .map(Parcelas::getParcelas)
            .map(list -> list.stream()
                .sorted(Comparator.comparing(Parcelas.Parcela::getDataVencimento))
                .map(p -> Parcela.builder()
                    .id(indice.getAndIncrement())
                    .valor(p.getValor())
                    .vencimento(p.getDataVencimento())
                    .build())
                .collect(Collectors.toList()))
            .orElse(emptyList());
    }

    private static BigDecimal buildTarifaCredito(final Despesas despesas) {

        return ofNullable(despesas)
            .map(d -> d.getDespesas()
                .stream()
                .map(Despesa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add))
            .orElse(BigDecimal.ZERO);
    }
}
