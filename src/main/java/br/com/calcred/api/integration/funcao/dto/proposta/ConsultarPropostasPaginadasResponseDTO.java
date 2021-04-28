package br.com.calcred.api.integration.funcao.dto.proposta;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@JsonDeserialize(builder = ConsultarPropostasPaginadasResponseDTO.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class ConsultarPropostasPaginadasResponseDTO implements Serializable {

    @JsonProperty("Propostas")
    Propostas propostas;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }

}