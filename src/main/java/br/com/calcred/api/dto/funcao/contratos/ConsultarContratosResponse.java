package br.com.calcred.api.dto.funcao.contratos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@JsonDeserialize(builder = ConsultarContratosResponse.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class ConsultarContratosResponse implements Serializable {

    List<Contrato> contratos;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }
}