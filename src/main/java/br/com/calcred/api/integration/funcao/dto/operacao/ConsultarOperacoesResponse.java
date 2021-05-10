package br.com.calcred.api.integration.funcao.dto.operacao;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@JsonDeserialize(builder = ConsultarOperacoesResponse.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class ConsultarOperacoesResponse implements Serializable {

    @JsonProperty("OutOperacaoCPF")
    List<Operacao> operacoes;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }

}