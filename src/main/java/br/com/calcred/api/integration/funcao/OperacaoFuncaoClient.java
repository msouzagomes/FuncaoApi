package br.com.calcred.api.integration.funcao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.calcred.api.integration.funcao.config.FuncaoFeignConfig;
import br.com.calcred.api.integration.funcao.config.OperacaoFeignIntegrationConfig;
import br.com.calcred.api.integration.funcao.dto.operacao.ConsultarOperacoesRequest;
import br.com.calcred.api.integration.funcao.dto.operacao.ConsultarOperacoesResponse;

@FeignClient(name = "OperacaoFuncaoClient", url = "${api.path.funcao.host}${api.path.funcao.operacao.basePath}", configuration = {
    FuncaoFeignConfig.class, OperacaoFeignIntegrationConfig.class})
public interface OperacaoFuncaoClient {

    @PostMapping(value = "${api.path.funcao.operacao.consultarOperacoes}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ConsultarOperacoesResponse consultarOperacoes(@RequestBody ConsultarOperacoesRequest request);
}
