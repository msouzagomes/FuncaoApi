package br.com.calcred.api.integration.funcao.dto.simulacao;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = Simulacoes.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class Simulacoes implements Serializable {

    @JsonProperty("Simulacao")
    Set<Simulacao> simulacao;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }
}