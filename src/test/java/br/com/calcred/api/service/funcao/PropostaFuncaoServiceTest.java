package br.com.calcred.api.service.funcao;

import static br.com.calcred.api.utils.RandomUtils.aleatorio;
import static br.com.calcred.api.utils.RandomUtils.gerarLista;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

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
import br.com.calcred.api.exception.BusinessErrorException;
import br.com.calcred.api.fixture.Fixture;
import br.com.calcred.api.integration.funcao.PropostaFuncaoClient;
import br.com.calcred.api.integration.funcao.dto.proposta.ConsultarPropostasPaginadasRequest;
import br.com.calcred.api.integration.funcao.dto.proposta.ConsultarPropostasPaginadasResponse;
import br.com.calcred.api.integration.funcao.dto.proposta.Proposta;
import br.com.calcred.api.integration.funcao.dto.proposta.Proposta.Esteira;
import br.com.calcred.api.integration.funcao.dto.proposta.Propostas;
import br.com.calcred.api.integration.funcao.dto.proposta.SituacaoEsteira;
import br.com.calcred.api.validator.CpfValidator;

@RunWith(MockitoJUnitRunner.class)
public class PropostaFuncaoServiceTest {

    @Captor
    private ArgumentCaptor<ConsultarPropostasPaginadasRequest> consultarPropostasPaginadasRequestArgumentCaptor;

    @InjectMocks
    private PropostaFuncaoService service;

    @Mock
    private PropostaFuncaoClient propostaFuncaoClient;

    @Mock
    private CpfValidator cpfValidator;

    @Test
    public void consultar_propostas_ok() {

        final String cpf = randomNumeric(11);
        final Integer pagina = nextInt();
        final Integer quantidade = nextInt();
        final OrdenacaoConsultaPropostas ordenacao = aleatorio(OrdenacaoConsultaPropostas.values());
        final List<Proposta> list = gerarLista(() -> Proposta.builder().esteira(
            Esteira.builder()
                .situacaoEsteira(aleatorio(SituacaoEsteira.values()).getValor())
                .build()).build(), 1, 10);
        final Propostas propostas = Fixture.make(Propostas.builder()).propostas(list).build();
        final ConsultarPropostasPaginadasResponse responseDTO = Fixture
            .make(ConsultarPropostasPaginadasResponse.builder()).propostas(propostas).build();

        when(propostaFuncaoClient
            .consultarPropostasPaginadas(consultarPropostasPaginadasRequestArgumentCaptor.capture()))
            .thenReturn(responseDTO);

        final ConsultarPropostasResponse response = service
            .consultarPropostasPaginadas(cpf, pagina, quantidade, ordenacao);

        verify(cpfValidator).validar(cpf);
        verify(propostaFuncaoClient)
            .consultarPropostasPaginadas(consultarPropostasPaginadasRequestArgumentCaptor.capture());

        final ConsultarPropostasPaginadasRequest actual = consultarPropostasPaginadasRequestArgumentCaptor.getValue();
        assertEquals(actual.getOrdenacao().getTipoOrdenacao(), ordenacao.getValor().getTipoOrdenacao());
        assertEquals(actual.getOrdenacao().getCampoOrdenacao(), ordenacao.getValor().getCampoOrdenacao());
        assertEquals(actual.getPaginacao().getNumeroPagina(), pagina);
        assertEquals(actual.getPaginacao().getQuantidade(), quantidade);
        assertEquals(actual.getPesquisa().getCpf(), new CPFFormatter().format(cpf));

        responseDTO.getPropostas().getPropostas().forEach(expected -> {

            final br.com.calcred.api.dto.funcao.proposta.Proposta proposta = response.getPropostas()
                .get(responseDTO.getPropostas().getPropostas().indexOf(expected));

            assertEquals(proposta.getNumeroProposta(), expected.getNumeroProposta());
            assertEquals(proposta.getSituacaoEsteira().getValor(), expected.getEsteira().getSituacaoEsteira());
        });
    }

    @Test
    public void consultar_propostas_vazio() {

        final String cpf = randomNumeric(11);
        final Integer pagina = nextInt();
        final Integer quantidade = nextInt();
        final OrdenacaoConsultaPropostas ordenacao = aleatorio(OrdenacaoConsultaPropostas.values());

        doThrow(BusinessErrorException.class).when(propostaFuncaoClient)
            .consultarPropostasPaginadas(any(ConsultarPropostasPaginadasRequest.class));

        final ConsultarPropostasResponse response = service
            .consultarPropostasPaginadas(cpf, pagina, quantidade, ordenacao);

        verify(cpfValidator).validar(cpf);
        verify(propostaFuncaoClient)
            .consultarPropostasPaginadas(consultarPropostasPaginadasRequestArgumentCaptor.capture());

        assertTrue(response.getPropostas().isEmpty());
        final ConsultarPropostasPaginadasRequest actual = consultarPropostasPaginadasRequestArgumentCaptor.getValue();
        assertEquals(actual.getOrdenacao().getTipoOrdenacao(), ordenacao.getValor().getTipoOrdenacao());
        assertEquals(actual.getOrdenacao().getCampoOrdenacao(), ordenacao.getValor().getCampoOrdenacao());
        assertEquals(actual.getPaginacao().getNumeroPagina(), pagina);
        assertEquals(actual.getPaginacao().getQuantidade(), quantidade);
        assertEquals(actual.getPesquisa().getCpf(), new CPFFormatter().format(cpf));
    }

}