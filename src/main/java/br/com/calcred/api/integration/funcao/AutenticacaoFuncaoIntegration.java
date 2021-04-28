package br.com.calcred.api.integration.funcao;

import static br.com.calcred.api.exception.ErrorCodeEnum.ERROR_AUTENTICACAO_FUNCAO;
import static java.util.Optional.ofNullable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.calcred.api.exception.InternalException;
import br.com.calcred.api.helper.MessageHelper;
import br.com.calcred.api.integration.funcao.dto.AutenticarResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutenticacaoFuncaoIntegration {

    private final RestTemplate restTemplate;
    private final MessageHelper messageHelper;

    @Value("${authentication.credentials.funcao.client-secret}")
    String clientSecret;

    @Value("${authentication.credentials.funcao.grant-type}")
    String grantType;

    @Value("${authentication.credentials.funcao.username}")
    String username;

    @Value("${authentication.credentials.funcao.client-id}")
    String clientId;

    @Value("${authentication.credentials.funcao.password}")
    String password;

    @Value("${api.path.funcao.autenticacao}")
    String urlBase;

    public String getToken(String url) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + urlBase);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(getBody(), getHeaders());

        try {
            ResponseEntity<AutenticarResponse> response = restTemplate
                .postForEntity(builder.build().encode().toUri(), request, AutenticarResponse.class);

            return ofNullable(response)
                .map(HttpEntity::getBody)
                .map(AutenticarResponse::getAccessToken)
                .orElseThrow(() -> new InternalException(messageHelper.get(ERROR_AUTENTICACAO_FUNCAO)));

        } catch (final HttpStatusCodeException ex) {

            log.error(
                "Erro ao obter token de autenticação na API Função, para a url {}. HttpStatusCode: {}. ResponseBody: {}",
                url, ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new InternalException(messageHelper.get(ERROR_AUTENTICACAO_FUNCAO));
        }
    }

    private HttpHeaders getHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return headers;
    }

    private MultiValueMap<String, String> getBody() {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_secret", clientSecret);
        map.add("grant_type", grantType);
        map.add("username", username);
        map.add("password", password);
        map.add("client_id", clientId);

        return map;
    }

}