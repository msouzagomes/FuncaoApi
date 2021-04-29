package br.com.calcred.api.service.funcao;

import static br.com.calcred.api.utils.RandomUtils.aleatorio;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.calcred.api.dto.funcao.proposta.ConsultarPropostasResponse;
import br.com.calcred.api.dto.funcao.proposta.OrdenacaoConsultaPropostas;
import br.com.calcred.api.dto.funcao.proposta.Proposta;
import br.com.calcred.api.fixture.Fixture;
import br.com.calcred.api.integration.funcao.PropostaFuncaoClient;
import br.com.calcred.api.integration.funcao.dto.proposta.ConsultarPropostasPaginadasRequestDTO;
import br.com.calcred.api.integration.funcao.dto.proposta.ConsultarPropostasPaginadasResponseDTO;
import br.com.calcred.api.validator.CpfValidator;

@RunWith(MockitoJUnitRunner.class)
public class PropostaFuncaoServiceTest {

    @Captor
    private ArgumentCaptor<ConsultarPropostasPaginadasRequestDTO> consultarPropostasPaginadasRequestArgumentCaptor;

    @InjectMocks
    private PropostaFuncaoService service;

    @Mock
    private PropostaFuncaoClient propostaFuncaoClient;

    @Mock
    private CpfValidator cpfValidator;

    @Test
    public void consultar_propostas_ok() {

        String cpf = randomNumeric(11);
        Integer pagina = nextInt();
        Integer quantidade = nextInt();
        OrdenacaoConsultaPropostas ordenacao = aleatorio(OrdenacaoConsultaPropostas.values());
        ConsultarPropostasPaginadasResponseDTO responseDTO = Fixture
            .make(ConsultarPropostasPaginadasResponseDTO.builder().build());

        when(propostaFuncaoClient
            .consultarPropostasPaginadas(consultarPropostasPaginadasRequestArgumentCaptor.capture()))
            .thenReturn(responseDTO);

        ConsultarPropostasResponse response = service.consultarPropostasPaginadas(cpf, pagina, quantidade, ordenacao);

        verify(cpfValidator).validar(cpf);
        verify(propostaFuncaoClient)
            .consultarPropostasPaginadas(consultarPropostasPaginadasRequestArgumentCaptor.capture());

        ConsultarPropostasPaginadasRequestDTO actual = consultarPropostasPaginadasRequestArgumentCaptor.getValue();
        assertEquals(actual.getOrdenacao().getTipoOrdenacao(), ordenacao.getValor().getTipoOrdenacao());
        assertEquals(actual.getOrdenacao().getCampoOrdenacao(), ordenacao.getValor().getCampoOrdenacao());
        assertEquals(actual.getPaginacao().getNumeroPagina(), pagina);
        assertEquals(actual.getPaginacao().getQuantidade(), quantidade);
        assertEquals(actual.getPesquisa().getCpf(), new CPFFormatter().format(cpf));

        responseDTO.getPropostas().getPropostas().forEach(expected -> {

            Proposta proposta = response.getPropostas()
                .get(responseDTO.getPropostas().getPropostas().indexOf(expected));

            assertEquals(proposta.getNumeroProposta(), proposta.getNumeroProposta());
            assertEquals(proposta.getSituacaoEsteira(), proposta.getSituacaoEsteira());
        });
    }

}