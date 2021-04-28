package br.com.calcred.api.service.funcao;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.calcred.api.builder.ConsultarPropostasPaginadasRequestDTOMapper;
import br.com.calcred.api.builder.PropostaBuilder;
import br.com.calcred.api.dto.funcao.proposta.ConsultarPropostasResponse;
import br.com.calcred.api.dto.funcao.proposta.OrdenacaoConsultaPropostas;
import br.com.calcred.api.dto.funcao.proposta.Proposta;
import br.com.calcred.api.integration.funcao.PropostaFuncaoClient;
import br.com.calcred.api.integration.funcao.dto.proposta.ConsultarPropostasPaginadasRequestDTO;
import br.com.calcred.api.integration.funcao.dto.proposta.ConsultarPropostasPaginadasResponseDTO;
import br.com.calcred.api.integration.funcao.dto.proposta.Propostas;
import br.com.calcred.api.validator.CpfValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PropostaFuncaoService {

    private final PropostaFuncaoClient propostaFuncaoClient;
    private final CpfValidator cpfValidator;

    public ConsultarPropostasResponse consultarPropostasPaginadas(String cpf, Integer pagina, Integer quantidade,
        OrdenacaoConsultaPropostas ordenacao) {

        cpfValidator.validar(cpf);

        log.debug("Consultando as propostas para o cliente {}" + sha256Hex(cpf));

        ConsultarPropostasPaginadasRequestDTO request = ConsultarPropostasPaginadasRequestDTOMapper
            .buildConsultarPropostasPaginadasRequestDTO(cpf, pagina, quantidade, ordenacao);

        ConsultarPropostasPaginadasResponseDTO consultarPropostasPaginadasResponsedto = propostaFuncaoClient
            .consultarPropostasPaginadas(request);

        List<Proposta> propostas = ofNullable(consultarPropostasPaginadasResponsedto)
            .map(ConsultarPropostasPaginadasResponseDTO::getPropostas)
            .map(Propostas::getPropostas)
            .map(p -> p.stream()
                .map(PropostaBuilder::buildProposta)
                .collect(toList()))
            .orElse(emptyList());

        return ConsultarPropostasResponse.builder().propostas(propostas).build();
    }
}
