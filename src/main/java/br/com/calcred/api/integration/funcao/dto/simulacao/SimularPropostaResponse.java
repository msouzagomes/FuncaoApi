package br.com.calcred.api.integration.funcao.dto.simulacao;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@JsonDeserialize(builder = SimularPropostaResponse.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class SimularPropostaResponse implements Serializable {

    @JsonProperty("Simulacoes")
    Simulacoes simulacoes;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }

}