package br.com.calcred.api.integration.funcao.config;

import static org.apache.commons.lang3.StringUtils.SPACE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.com.calcred.api.integration.funcao.AutenticacaoFuncaoIntegration;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
abstract class FuncaoAutenticacaoFeignConfig implements RequestInterceptor {

    abstract String getUrlModulo();

    abstract String getClientId();

    @Autowired
    private AutenticacaoFuncaoIntegration autenticacao;

    @Override
    public void apply(final RequestTemplate requestTemplate) {

        log.info("Realizando autenticação no Função para o módulo {}", getUrlModulo());

        final String token = autenticacao.getToken(getUrlModulo(), getClientId());

        requestTemplate.header("Authorization", "Bearer" + SPACE + token);

    }
}