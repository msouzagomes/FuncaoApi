package br.com.calcred.api.resource.v1.funcao;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.calcred.api.dto.funcao.proposta.ConsultarPropostasResponse;
import br.com.calcred.api.dto.funcao.proposta.OrdenacaoConsultaPropostas;
import br.com.calcred.api.dto.funcao.simulacao.SimularPropostasRequest;
import br.com.calcred.api.dto.funcao.simulacao.SimularPropostasResponse;
import br.com.calcred.api.service.funcao.PropostaFuncaoService;
import br.com.calcred.api.service.funcao.SimulacaoFuncaoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1")
public class FuncaoResource {

    private final PropostaFuncaoService propostaFuncaoService;
    private final SimulacaoFuncaoService simulacaoFuncaoService;

    @GetMapping("/propostas/{cpf}")
    public ConsultarPropostasResponse consultarPropostas(
        @PathVariable final String cpf,
        @RequestParam(value = "numeroPagina", defaultValue = "1") final Integer pagina,
        @RequestParam(value = "ordenacao", defaultValue = "NUMERO_PROPOSTA_DECRESCENTE") final OrdenacaoConsultaPropostas ordenacao,
        @RequestParam(value = "quantidadePagina", defaultValue = "50") final Integer quantidade) {

        return propostaFuncaoService.consultarPropostasPaginadas(cpf, pagina, quantidade, ordenacao);
    }

    @PostMapping("/simulacao")
    public SimularPropostasResponse simularPropostas(@RequestBody @Valid final SimularPropostasRequest request) {

        return simulacaoFuncaoService.simularPropostas(request);
    }
}
