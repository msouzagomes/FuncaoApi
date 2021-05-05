package br.com.calcred.api.service.funcao;

import static br.com.calcred.api.builder.SimularPropostaRequestDTOMapper.buildSimularPropostaRequestDTO;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.calcred.api.builder.SimulacaoMapper;
import br.com.calcred.api.dto.funcao.simulacao.Simulacao;
import br.com.calcred.api.dto.funcao.simulacao.SimularPropostasResponse;
import br.com.calcred.api.integration.funcao.SimulacaoFuncaoClient;
import br.com.calcred.api.integration.funcao.dto.erro.Codigo;
import br.com.calcred.api.integration.funcao.dto.erro.StatusResponse;
import br.com.calcred.api.integration.funcao.dto.simulacao.Simulacoes;
import br.com.calcred.api.integration.funcao.dto.simulacao.SimularPropostaResponse;
import br.com.calcred.api.integration.funcao.dto.simulacao.SimularPropostasRequest;
import br.com.calcred.api.validator.CpfValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SimulacaoFuncaoService {

    private final SimulacaoFuncaoClient simulacaoFuncaoClient;
    private final CpfValidator cpfValidator;

    public SimularPropostasResponse simularPropostas(
        final br.com.calcred.api.dto.funcao.simulacao.SimularPropostasRequest request) {

        cpfValidator.validar(request.getCpf());

        log.info("Simulando proposta para {}, com código produto {} e código da tabela {}", sha256Hex(request.getCpf()),
            request.getCodigoProduto(), request.getCodigoTabela());

        final SimularPropostasRequest requestDTO = buildSimularPropostaRequestDTO(request);

        final SimularPropostaResponse responseDTO = simulacaoFuncaoClient.simularProposta(requestDTO);

        final List<Simulacao> simulacoes = ofNullable(responseDTO)
            .map(SimularPropostaResponse::getSimulacoes)
            .map(Simulacoes::getSimulacao)
            .map(list -> list.stream()
                .filter(simulacao ->
                    ofNullable(simulacao.getStatusResponse())
                        .map(StatusResponse::getCodigo)
                        .orElse(null) == Codigo.SUCESSO)
                .filter(simulacao -> simulacao.getQuantidadeParcelas() <= request.getQuantidadeMaximaParcelas())
                .filter(simulacao -> simulacao.getQuantidadeParcelas() >= request.getQuantidadeMinimaParcelas())
                .map(SimulacaoMapper::buildSimulacao)
                .sorted(Comparator.comparing(Simulacao::getQuantidadeParcelas))
                .collect(toList()))
            .orElse(emptyList());

        return SimularPropostasResponse.builder().simulacoes(simulacoes).build();
    }
}
