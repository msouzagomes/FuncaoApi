package br.com.calcred.api.dto.funcao.contratos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import br.com.calcred.api.integration.funcao.dto.operacao.SituacaoOperacao;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@JsonDeserialize(builder = Contrato.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class Contrato implements Serializable {

    String numeroContrato;

    BigDecimal valorCredito;

    Long quantidadeParcelas;

    SituacaoOperacao situacaoOperacao;

    BigDecimal valorParcela;

    LocalDate dataLiquidacao;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }

}