package br.com.calcred.api.integration.funcao.dto.autenticacao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@JsonDeserialize(builder = AutenticarResponse.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class AutenticarResponse {

    @JsonProperty("access_token")
    String accessToken;

    @JsonProperty("token_type")
    String tokenType;

    @JsonProperty("expires_in")
    Long expiresIn;

    @JsonProperty("refresh_token")
    String refreshToken;

    @JsonProperty("userName")
    String userName;

    @JsonProperty("client_id")
    String clientId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }

}