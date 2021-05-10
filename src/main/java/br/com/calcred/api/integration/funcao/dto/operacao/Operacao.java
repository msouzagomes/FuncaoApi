package br.com.calcred.api.integration.funcao.dto.operacao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@JsonDeserialize(builder = Operacao.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class Operacao implements Serializable {

    @JsonProperty("Operacao")
    String numeroOperacao;

    @JsonProperty("ValorCredito")
    BigDecimal valorCredito;

    @JsonProperty("QuantidadeParcelas")
    Long quantidadeParcelas;

    @JsonProperty("SituacaoContrato")
    SituacaoOperacao situacaoOperacao;

    @JsonProperty("ValorParcela")
    BigDecimal valorParcela;

    @JsonProperty("DataLiquidacaoContrato")
    LocalDate dataLiquidacao;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }

}