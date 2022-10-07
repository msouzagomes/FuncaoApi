package br.com.calcred.api.integration.funcao.dto.proposta;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = Propostas.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class Propostas implements Serializable {

    @JsonProperty("Propostas")
    List<Proposta> propostas;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }
}