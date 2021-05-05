package br.com.calcred.api.service.funcao;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import br.com.calcred.api.builder.SimulacaoMapper;
import br.com.calcred.api.fixture.Fixture;
import br.com.calcred.api.integration.funcao.SimulacaoFuncaoClient;
import br.com.calcred.api.integration.funcao.dto.erro.Codigo;
import br.com.calcred.api.integration.funcao.dto.erro.FuncaoStatusBody;
import br.com.calcred.api.integration.funcao.dto.simulacao.Simulacao;
import br.com.calcred.api.integration.funcao.dto.simulacao.SimularPropostaResponse;
import br.com.calcred.api.integration.funcao.dto.simulacao.SimularPropostasRequest;
import br.com.calcred.api.validator.CpfValidator;

@RunWith(MockitoJUnitRunner.class)
public class SimulacaoFuncaoServiceTest {

    @Captor
    private ArgumentCaptor<SimularPropostasRequest> simularPropostaRequestDTOArgumentCaptor;

    @InjectMocks
    private SimulacaoFuncaoService service;

    @Mock
    private SimulacaoFuncaoClient simulacaoFuncaoClient;

    @Mock
    private CpfValidator cpfValidator;

    @Test
    public void simular_propostas_ok() {

        final int quantidadeMaximaParcelas = nextInt(51, 100);
        final int quantidadeMinimaParcelas = nextInt(0, 49);
        final br.com.calcred.api.dto.funcao.simulacao.SimularPropostasRequest request = Fixture.make(
            br.com.calcred.api.dto.funcao.simulacao.SimularPropostasRequest.builder())
            .quantidadeMaximaParcelas(quantidadeMaximaParcelas)
            .quantidadeMinimaParcelas(quantidadeMinimaParcelas)
            .build();
        final SimularPropostaResponse responseDTO = Fixture.make(SimularPropostaResponse.builder().build());

        final Simulacao simulacaoSucesso = Fixture
            .make(Simulacao.builder()).statusBody(FuncaoStatusBody.builder().codigo(Codigo.SUCESSO).build())
            .quantidadeParcelas(nextInt(quantidadeMinimaParcelas, quantidadeMaximaParcelas)).build();
        responseDTO.getSimulacoes().getSimulacao().add(simulacaoSucesso);
        final Simulacao simulacaoMinima = Fixture
            .make(Simulacao.builder()).statusBody(FuncaoStatusBody.builder().codigo(Codigo.SUCESSO).build())
            .quantidadeParcelas(quantidadeMinimaParcelas - nextInt()).build();
        responseDTO.getSimulacoes().getSimulacao().add(simulacaoMinima);
        final Simulacao simulacaoMaxima = Fixture
            .make(Simulacao.builder()).statusBody(FuncaoStatusBody.builder().codigo(Codigo.SUCESSO).build())
            .quantidadeParcelas(quantidadeMaximaParcelas + nextInt()).build();
        responseDTO.getSimulacoes().getSimulacao().add(simulacaoMaxima);
        final Simulacao simulacaoErro = Fixture
            .make(Simulacao.builder()).statusBody(FuncaoStatusBody.builder().codigo(Codigo.ERRO).build())
            .quantidadeParcelas(nextInt(quantidadeMinimaParcelas, quantidadeMaximaParcelas)).build();
        responseDTO.getSimulacoes().getSimulacao().add(simulacaoErro);

        when(simulacaoFuncaoClient.simularProposta(any(SimularPropostasRequest.class))).thenReturn(responseDTO);

        final List<br.com.calcred.api.dto.funcao.simulacao.Simulacao> actualResponse = service.simularPropostas(request)
            .getSimulacoes();

        verify(cpfValidator).validar(request.getCpf());
        verify(simulacaoFuncaoClient).simularProposta(simularPropostaRequestDTOArgumentCaptor.capture());

        final SimularPropostasRequest actualSimulacaoRequest = simularPropostaRequestDTOArgumentCaptor.getValue();

        assertEquals(request.getCorrespondenteBancario(), actualSimulacaoRequest.getOrigens().getOrigem3());
        assertEquals(request.getCodigoLoja(), actualSimulacaoRequest.getOrigens().getOrigem4());
        assertEquals(request.getGerente(), actualSimulacaoRequest.getOrigens().getOrigem5());
        assertEquals(request.getPromotorLoja(), actualSimulacaoRequest.getOrigens().getOrigem3O());

        assertEquals(request.getCodigoProduto(), actualSimulacaoRequest.getCondicaoCredito().getCodigoProduto());
        assertEquals(request.getCodigoTabela(), actualSimulacaoRequest.getCondicaoCredito().getCodigoTabela());
        assertEquals(request.getValorFinanciado(), actualSimulacaoRequest.getCondicaoCredito().getValorFinanciado());
        assertEquals(request.getDataBase().toLocalDateTime(),
            actualSimulacaoRequest.getCondicaoCredito().getDataBase());
        assertEquals(request.getDataPrimeiroVencimento().toLocalDateTime(),
            actualSimulacaoRequest.getCondicaoCredito().getDataPrimeiroVencimento());
        assertEquals(request.getTipoPrimeiroVencimento().getValor(),
            actualSimulacaoRequest.getCondicaoCredito().getTipoPrimeiroVencimento());
        assertEquals(request.getDataNascimento().toLocalDateTime(),
            actualSimulacaoRequest.getCondicaoCredito().getDadoCadastral().getDataNascimento());
        assertEquals(request.getTipoPessoa().getValor(),
            actualSimulacaoRequest.getCondicaoCredito().getDadoCadastral().getTipoPessoa());
        assertEquals(request.getCpf(), actualSimulacaoRequest.getCondicaoCredito().getDadoCadastral().getCPFCNPJ());
        assertEquals(request.getRegimeDeTributacao().getValor(),
            actualSimulacaoRequest.getCondicaoCredito().getDadoCadastral().getRegimeDeTributacao());
        assertEquals(request.getPne().getValor(),
            actualSimulacaoRequest.getCondicaoCredito().getDadoCadastral().getPne());
        assertEquals(request.getUf().name(), actualSimulacaoRequest.getCondicaoCredito().getDadoCadastral().getUf());

        assertTrue(
            actualResponse.stream().anyMatch(l -> l.equals(SimulacaoMapper.buildSimulacao(simulacaoSucesso))));
        assertTrue(
            actualResponse.stream().noneMatch(l -> l.equals(SimulacaoMapper.buildSimulacao(simulacaoMinima))));
        assertTrue(
            actualResponse.stream().noneMatch(l -> l.equals(SimulacaoMapper.buildSimulacao(simulacaoMaxima))));
        assertTrue(actualResponse.stream().noneMatch(l -> l.equals(SimulacaoMapper.buildSimulacao(simulacaoErro))));
    }
}