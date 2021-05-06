package br.com.calcred.api.integration.funcao.config;

import static org.apache.commons.lang3.StringUtils.SPACE;

import org.springframework.context.annotation.Bean;

import br.com.calcred.api.integration.funcao.AutenticacaoFuncaoIntegration;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FuncaoAutenticacaoFeignConfig {


    private final AutenticacaoFuncaoIntegration autenticacao;

    @Bean
    RequestInterceptor requestInterceptor() {
        return requestTemplate -> {

            final String token = autenticacao.getToken(requestTemplate.feignTarget().url());

            requestTemplate.header("Authorization", "Bearer" + SPACE + token);
        };
    }
}