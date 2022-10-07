package br.com.calcred.api.integration.funcao.dto.erro;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = FuncaoErrorBody.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class FuncaoErrorBody implements Serializable {

    @JsonProperty("ERRO")
    List<Erro> erros;

    @Value
    @JsonDeserialize(builder = Erro.JacksonBuilder.class)
    @Builder(builderClassName = "JacksonBuilder")
    public static class Erro implements Serializable {

        @JsonProperty("MENSAGEM")
        String mensagem;

        @JsonProperty("CODIGO")
        CodigoError codigo;

        @JsonPOJOBuilder(withPrefix = "")
        public static class JacksonBuilder {

        }
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }
}