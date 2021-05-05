package br.com.calcred.api.integration.funcao.dto.erro;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@JsonDeserialize(builder = StatusResponse.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class StatusResponse {

    @JsonProperty("CODIGO")
    Codigo codigo;

    @JsonProperty("ERROS")
    Erros erros;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }

}