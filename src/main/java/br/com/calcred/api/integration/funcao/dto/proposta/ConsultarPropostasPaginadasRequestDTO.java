package br.com.calcred.api.integration.funcao.dto.proposta;

import java.io.Serializable;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@Builder
public class ConsultarPropostasPaginadasRequestDTO implements Serializable {

    Ordenacao ordenacao;
    Paginacao paginacao;
    Pesquisa pesquisa;

    @Value
    @Builder
    public static class Ordenacao implements Serializable {

        String campoOrdenacao;
        String tipoOrdenacao;
    }

    @Value
    @Builder
    public static class Paginacao implements Serializable {

        Integer numeroPagina;
        Integer quantidade;
    }

    @Value
    @Builder
    public static class Pesquisa implements Serializable {

        String cpf;
    }
}