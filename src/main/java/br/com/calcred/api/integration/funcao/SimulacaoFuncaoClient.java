package br.com.calcred.api.integration.funcao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.calcred.api.integration.funcao.config.FuncaoFeignConfig;
import br.com.calcred.api.integration.funcao.config.SimulacaoFeignIntegrationConfig;
import br.com.calcred.api.integration.funcao.dto.simulacao.SimularPropostaResponse;
import br.com.calcred.api.integration.funcao.dto.simulacao.SimularPropostasRequest;

@FeignClient(name = "SimulacaoFuncaoClient", url = "${api.path.funcao.host}${api.path.funcao.simulacao.basePath}",
    configuration = {FuncaoFeignConfig.class, SimulacaoFeignIntegrationConfig.class})
public interface SimulacaoFuncaoClient {

    @PostMapping(value = "${api.path.funcao.simulacao.simulacaoProposta}", consumes = MediaType.APPLICATION_JSON_VALUE)
    SimularPropostaResponse simularProposta(@RequestBody SimularPropostasRequest request);
}
