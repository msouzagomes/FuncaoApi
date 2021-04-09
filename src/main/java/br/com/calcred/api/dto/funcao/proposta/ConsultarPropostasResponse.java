package br.com.calcred.api.dto.funcao.proposta;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@JsonDeserialize(builder = ConsultarPropostasResponse.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class ConsultarPropostasResponse implements Serializable {

    List<Proposta> propostas;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }
}