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
@JsonDeserialize(builder = Proposta.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class Proposta {

    @JsonProperty("NumeroProposta")
    String numeroProposta;

    @JsonProperty("Esteira")
    Esteira esteira;

    @Value
    @JsonDeserialize(builder = Esteira.JacksonBuilder.class)
    @Builder(builderClassName = "JacksonBuilder")
    public static class Esteira implements Serializable {

        @JsonProperty("SituacaoEsteira")
        String situacaoEsteira;

        @JsonPOJOBuilder(withPrefix = "")
        public static class JacksonBuilder {

        }
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }

}