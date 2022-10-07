package br.com.calcred.api.integration.funcao.dto.simulacao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = Despesas.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class Despesas implements Serializable {

    @JsonProperty("Despesa")
    List<Despesa> despesas;

    @Value
    @JsonDeserialize(builder = Despesa.JacksonBuilder.class)
    @Builder(builderClassName = "JacksonBuilder")
    public static class Despesa implements Serializable {

        @JsonProperty("Valor")
        BigDecimal valor;

        @JsonPOJOBuilder(withPrefix = "")
        public static class JacksonBuilder {

        }
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }
}