package br.com.calcred.api.integration.funcao.config;

import static org.apache.commons.lang3.StringUtils.SPACE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.com.calcred.api.helper.MessageHelper;
import br.com.calcred.api.integration.funcao.AutenticacaoFuncaoIntegration;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class FuncaoFeignIntegrationConfig implements RequestInterceptor {

    @Autowired
    private MessageHelper messageHelper;

    @Autowired
    private AutenticacaoFuncaoIntegration autenticacao;

    @Override
    public void apply(final RequestTemplate requestTemplate) {

        final String token = autenticacao.getToken(requestTemplate.feignTarget().url());

        requestTemplate.header("Authorization", "Bearer" + SPACE + token);
    }
}