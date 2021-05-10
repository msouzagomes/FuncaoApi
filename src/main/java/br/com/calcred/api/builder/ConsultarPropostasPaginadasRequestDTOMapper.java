package br.com.calcred.api.builder;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.calcred.api.dto.funcao.proposta.OrdenacaoConsultaPropostas;
import br.com.calcred.api.integration.funcao.dto.proposta.ConsultarPropostasPaginadasRequest;
import br.com.calcred.api.integration.funcao.dto.proposta.ConsultarPropostasPaginadasRequest.Paginacao;
import br.com.calcred.api.integration.funcao.dto.proposta.ConsultarPropostasPaginadasRequest.Pesquisa;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsultarPropostasPaginadasRequestDTOMapper {

    public static ConsultarPropostasPaginadasRequest buildConsultarPropostasPaginadasRequestDTO(final String cpf,
        final Integer pagina, final Integer quantidade, final OrdenacaoConsultaPropostas ordenacao) {

        return ConsultarPropostasPaginadasRequest.builder()
            .ordenacao(ordenacao.getValor())
            .pesquisa(Pesquisa.builder()
                .cpf(new CPFFormatter().format(cpf))
                .build())
            .paginacao(Paginacao.builder()
                .numeroPagina(pagina)
                .quantidade(quantidade)
                .build())
            .build();
    }
}
