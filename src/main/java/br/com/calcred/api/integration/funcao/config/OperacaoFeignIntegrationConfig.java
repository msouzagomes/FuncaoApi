package br.com.calcred.api.integration.funcao.config;

import org.springframework.beans.factory.annotation.Value;

public class OperacaoFeignIntegrationConfig extends FuncaoAutenticacaoFeignConfig {

    @Value("${api.path.funcao.operacao.basePath}")
    String urlModulo;

    @Value("${api.path.funcao.operacao.credencial.client-id}")
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