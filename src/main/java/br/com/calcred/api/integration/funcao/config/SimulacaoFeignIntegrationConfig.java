package br.com.calcred.api.integration.funcao.config;

import org.springframework.beans.factory.annotation.Value;

public class SimulacaoFeignIntegrationConfig extends FuncaoAutenticacaoFeignConfig {

    @Value("${api.path.funcao.simulacao.basePath}")
    String urlModulo;

    @Value("${api.path.funcao.simulacao.credencial.client-id}")
    String clientId;

    @Override
    String getUrlModulo() {
        return urlModulo;
    }

    @Override
    String getClientId() {
        return clientId;
    }
}