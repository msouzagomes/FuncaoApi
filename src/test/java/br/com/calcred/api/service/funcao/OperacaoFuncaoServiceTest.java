package br.com.calcred.api.service.funcao;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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
import br.com.calcred.api.dto.funcao.contratos.ConsultarContratosResponse;
import br.com.calcred.api.dto.funcao.contratos.Contrato;
import br.com.calcred.api.exception.BusinessErrorException;
import br.com.calcred.api.fixture.Fixture;
import br.com.calcred.api.integration.funcao.OperacaoFuncaoClient;
import br.com.calcred.api.integration.funcao.dto.operacao.ConsultarOperacoesRequest;
import br.com.calcred.api.integration.funcao.dto.operacao.ConsultarOperacoesResponse;
import br.com.calcred.api.validator.CpfValidator;

@RunWith(MockitoJUnitRunner.class)
public class OperacaoFuncaoServiceTest {

    @Captor
    private ArgumentCaptor<ConsultarOperacoesRequest> operacoesRequestArgumentCaptor;

    @InjectMocks
    private OperacaoFuncaoService service;

    @Mock
    private OperacaoFuncaoClient operacaoFuncaoClient;

    @Mock
    private CpfValidator cpfValidator;

    @Test
    public void consultar_operacoes_ok() {

        final String cpf = randomNumeric(11);
        final ConsultarOperacoesResponse responseDTO = Fixture.make(ConsultarOperacoesResponse.builder().build());

        when(operacaoFuncaoClient.consultarOperacoes(any(ConsultarOperacoesRequest.class))).thenReturn(responseDTO);

        final ConsultarContratosResponse response = service.consultarContratos(cpf);

        verify(cpfValidator).validar(cpf);
        verify(operacaoFuncaoClient).consultarOperacoes(operacoesRequestArgumentCaptor.capture());

        assertEquals(new CPFFormatter().format(cpf), operacoesRequestArgumentCaptor.getValue().getCpf());

        responseDTO.getOperacoes().forEach(expected -> {

            final Contrato actual = response.getContratos().get(responseDTO.getOperacoes().indexOf(expected));

            assertEquals(expected.getSituacaoOperacao(), actual.getSituacaoOperacao());
            assertEquals(expected.getDataLiquidacao(), actual.getDataLiquidacao());
            assertEquals(expected.getQuantidadeParcelas(), actual.getQuantidadeParcelas());
            assertEquals(expected.getValorParcela(), actual.getValorParcela());
            assertEquals(expected.getNumeroOperacao(), actual.getNumeroContrato());
            assertEquals(expected.getValorCredito(), actual.getValorCredito());
        });
    }

    @Test
    public void consultar_operacoes_vazio() {

        final String cpf = randomNumeric(11);
        final ConsultarOperacoesResponse responseDTO = Fixture.make(ConsultarOperacoesResponse.builder().build());

        doThrow(BusinessErrorException.class).when(operacaoFuncaoClient)
            .consultarOperacoes(any(ConsultarOperacoesRequest.class));

        assertTrue(service.consultarContratos(cpf).getContratos().isEmpty());

        verify(cpfValidator).validar(cpf);
        verify(operacaoFuncaoClient).consultarOperacoes(operacoesRequestArgumentCaptor.capture());

        assertEquals(new CPFFormatter().format(cpf), operacoesRequestArgumentCaptor.getValue().getCpf());
    }
}