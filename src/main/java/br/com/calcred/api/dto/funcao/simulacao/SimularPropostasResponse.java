package br.com.calcred.api.dto.funcao.simulacao;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@JsonDeserialize(builder = SimularPropostasResponse.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class SimularPropostasResponse implements Serializable {

    List<Simulacao> simulacoes;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }
}