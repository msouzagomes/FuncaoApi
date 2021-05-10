package br.com.calcred.api.service.funcao;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import org.springframework.stereotype.Service;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.calcred.api.builder.ContratoBuilder;
import br.com.calcred.api.dto.funcao.contratos.ConsultarContratosResponse;
import br.com.calcred.api.exception.BusinessErrorException;
import br.com.calcred.api.integration.funcao.OperacaoFuncaoClient;
import br.com.calcred.api.integration.funcao.dto.operacao.ConsultarOperacoesRequest;
import br.com.calcred.api.integration.funcao.dto.operacao.ConsultarOperacoesResponse;
import br.com.calcred.api.validator.CpfValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OperacaoFuncaoService {

    private final OperacaoFuncaoClient operacaoFuncaoClient;
    private final CpfValidator cpfValidator;

    public ConsultarContratosResponse consultarContratos(final String cpf) {

        cpfValidator.validar(cpf);

        log.info("Consultando operações para o cliente {}", sha256Hex(cpf));

        final ConsultarOperacoesRequest request = ConsultarOperacoesRequest.builder()
            .cpf(new CPFFormatter().format(cpf))
            .build();

        try {
            final ConsultarOperacoesResponse response = operacaoFuncaoClient.consultarOperacoes(request);

            return ofNullable(response)
                .map(ConsultarOperacoesResponse::getOperacoes)
                .map(o -> o.stream()
                    .map(ContratoBuilder::buildContrato)
                    .collect(toList()))
                .map(list -> ConsultarContratosResponse.builder().contratos(list).build())
                .orElseGet(() -> ConsultarContratosResponse.builder().contratos(emptyList()).build());
        } catch (final BusinessErrorException exception) {
            return ConsultarContratosResponse.builder().contratos(emptyList()).build();
        }
    }
}