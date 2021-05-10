package br.com.calcred.api.integration.funcao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.calcred.api.integration.funcao.config.FuncaoFeignConfig;
import br.com.calcred.api.integration.funcao.config.PropostaFeignIntegrationConfig;
import br.com.calcred.api.integration.funcao.dto.proposta.ConsultarPropostasPaginadasRequest;
import br.com.calcred.api.integration.funcao.dto.proposta.ConsultarPropostasPaginadasResponse;

@FeignClient(name = "PropostaFuncaoClient", url = "${api.path.funcao.host}${api.path.funcao.proposta.basePath}",
    configuration = {FuncaoFeignConfig.class, PropostaFeignIntegrationConfig.class})
public interface PropostaFuncaoClient {

    @PostMapping(value = "${api.path.funcao.proposta.consultarPropostasPaginadas}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ConsultarPropostasPaginadasResponse consultarPropostasPaginadas(
        @RequestBody ConsultarPropostasPaginadasRequest request);
}
