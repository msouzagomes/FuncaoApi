package br.com.calcred.api.dto.funcao.proposta;


import br.com.calcred.api.integration.funcao.dto.proposta.ConsultarPropostasPaginadasRequestDTO.Ordenacao;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrdenacaoConsultaPropostas {

    NUMERO_PROPOSTA_DECRESCENTE(
        Ordenacao.builder().campoOrdenacao(CampoOrdenacao.NUMERO_PROPOSTA.getValor())
            .tipoOrdenacao(TipoOrdenacao.DECRESCENTE.getValor()).build());

    Ordenacao valor;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum CampoOrdenacao {

        NUMERO_PROPOSTA("NumeroProposta");

        String valor;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum TipoOrdenacao {

        DECRESCENTE("Descendente");

        String valor;
    }
}