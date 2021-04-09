package br.com.calcred.api.resource.v1.funcao;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.calcred.api.dto.funcao.proposta.ConsultarPropostasResponse;
import br.com.calcred.api.dto.funcao.proposta.OrdenacaoConsultaPropostas;
import br.com.calcred.api.service.funcao.PropostaFuncaoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class FuncaoResource {

    private final PropostaFuncaoService propostaFuncaoService;

    @GetMapping("/propostas/{cpf}")
    public ConsultarPropostasResponse consultarPropostas(
        @PathVariable String cpf,
        @RequestParam(value = "numeroPagina", defaultValue = "1") Integer pagina,
        @RequestParam(value = "ordenacao", defaultValue = "NUMERO_PROPOSTA_DECRESCENTE") OrdenacaoConsultaPropostas ordenacao,
        @RequestParam(value = "quantidadePagina", defaultValue = "50") Integer quantidade) {

        return propostaFuncaoService.consultarPropostasPaginadas(cpf, pagina, quantidade, ordenacao);
    }
}
