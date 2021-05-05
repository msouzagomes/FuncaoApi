package br.com.calcred.api.builder;

import static java.util.Optional.ofNullable;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.calcred.api.dto.funcao.simulacao.Pne;
import br.com.calcred.api.dto.funcao.simulacao.RegimeDeTributacao;
import br.com.calcred.api.dto.funcao.simulacao.TipoPessoa;
import br.com.calcred.api.dto.funcao.simulacao.TipoPrimeiroVencimento;
import br.com.calcred.api.integration.funcao.dto.simulacao.SimularPropostasRequest;
import br.com.calcred.api.integration.funcao.dto.simulacao.SimularPropostasRequest.CondicaoCredito;
import br.com.calcred.api.integration.funcao.dto.simulacao.SimularPropostasRequest.CondicaoCredito.DadoCadastral;
import br.com.calcred.api.integration.funcao.dto.simulacao.SimularPropostasRequest.CondicaoCredito.Parcelas;
import br.com.calcred.api.integration.funcao.dto.simulacao.SimularPropostasRequest.CondicaoCredito.Parcelas.Parcela;
import br.com.calcred.api.integration.funcao.dto.simulacao.SimularPropostasRequest.Origens;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimularPropostaRequestDTOMapper {

    public static SimularPropostasRequest buildSimularPropostaRequestDTO(final
    br.com.calcred.api.dto.funcao.simulacao.SimularPropostasRequest request) {

        return ofNullable(request)
            .map(r -> SimularPropostasRequest.builder()
                .condicaoCredito(CondicaoCredito.builder()
                    .valorFinanciado(r.getValorFinanciado())
                    .dataPrimeiroVencimento(ofNullable(r.getDataPrimeiroVencimento())
                        .map(OffsetDateTime::toLocalDateTime)
                        .orElse(null))
                    .dadoCadastral(buildDadoCadastral(r))
                    .tipoPrimeiroVencimento(ofNullable(r.getTipoPrimeiroVencimento())
                        .map(TipoPrimeiroVencimento::getValor)
                        .orElse(null))
                    .dataBase(ofNullable(r.getDataBase())
                        .map(OffsetDateTime::toLocalDateTime)
                        .orElse(null))
                    .codigoTabela(r.getCodigoTabela())
                    .codigoProduto(r.getCodigoProduto())
                    .parcelas(Parcelas.builder()
                        .parcela(buildParcelas(r.getQuantidadeMaximaParcelas(), r.getQuantidadeMinimaParcelas()))
                        .build())
                    .build())
                .origens(Origens.builder()
                    .origem3(r.getCorrespondenteBancario())
                    .origem4(r.getCodigoLoja())
                    .origem5(r.getGerente())
                    .origem3O(r.getPromotorLoja())
                    .build())
                .build())
            .orElse(null);
    }

    private static DadoCadastral buildDadoCadastral(
        final br.com.calcred.api.dto.funcao.simulacao.SimularPropostasRequest request) {

        return DadoCadastral.builder()
            .uf(ofNullable(request.getUf())
                .map(Enum::name)
                .orElse(null))
            .regimeDeTributacao(ofNullable(request.getRegimeDeTributacao())
                .map(RegimeDeTributacao::getValor)
                .orElse(null))
            .pne(ofNullable(request.getPne())
                .map(Pne::getValor)
                .orElse(null))
            .dataNascimento((ofNullable(request.getDataNascimento())
                .map(OffsetDateTime::toLocalDateTime)
                .orElse(null)))
            .CPFCNPJ(request.getCpf())
            .tipoPessoa(ofNullable(request.getTipoPessoa())
                .map(TipoPessoa::getValor)
                .orElse(null))
            .build();
    }

    private static List<Parcela> buildParcelas(final Integer maximo, final Integer minimo) {

        final List<Parcela> parcelas = new ArrayList<>();

        for (int contador = minimo; contador <= maximo; ++contador) {
            parcelas.add(Parcela.builder()
                .quantidadeParcelas(contador)
                .build());
        }

        return parcelas;
    }
}
