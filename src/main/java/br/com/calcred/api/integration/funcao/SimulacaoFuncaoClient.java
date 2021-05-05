package br.com.calcred.api.integration.funcao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.calcred.api.integration.config.FeignIntegrationConfig;
import br.com.calcred.api.integration.funcao.config.FuncaoFeignIntegrationConfig;
import br.com.calcred.api.integration.funcao.dto.simulacao.SimularPropostaResponse;
import br.com.calcred.api.integration.funcao.dto.simulacao.SimularPropostasRequest;

@FeignClient(name = "SimulacaoFuncaoClient", url = "${api.path.funcao.host}${api.path.funcao.simulacao.baseUrl}",
    configuration = {FeignIntegrationConfig.class, FuncaoFeignIntegrationConfig.class})
public interface SimulacaoFuncaoClient {

    @PostMapping(value = "${api.path.funcao.simulacao.simulacaoProposta}", consumes = MediaType.APPLICATION_JSON_VALUE)
    SimularPropostaResponse simularProposta(@RequestBody SimularPropostasRequest request);
}
