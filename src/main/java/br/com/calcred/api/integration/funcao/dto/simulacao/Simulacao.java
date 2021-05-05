package br.com.calcred.api.integration.funcao.dto.simulacao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import br.com.calcred.api.integration.funcao.dto.erro.StatusResponse;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@JsonDeserialize(builder = Simulacao.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class Simulacao implements Serializable {

    @JsonProperty("QuantidadeParcelas")
    Integer quantidadeParcelas;

    @JsonProperty("ValorParcela")
    BigDecimal valorParcela;

    @JsonProperty("ValorFinanciado")
    BigDecimal valorFinanciado;

    @JsonProperty("ValorBruto")
    BigDecimal valorBruto;

    @JsonProperty("TaxaCETAM")
    BigDecimal taxaCetAm;

    @JsonProperty("TaxaCETAA")
    BigDecimal taxaCetAa;

    @JsonProperty("ValorIOF")
    BigDecimal valorIOF;

    @JsonProperty("TaxaCLAM")
    BigDecimal taxaClAm;

    @JsonProperty("STATUS")
    StatusResponse statusResponse;

    @JsonProperty("Despesas")
    Despesas despesas;

    @JsonProperty("Parcelas")
    Parcelas parcelas;

    @Value
    @JsonDeserialize(builder = Parcelas.JacksonBuilder.class)
    @Builder(builderClassName = "JacksonBuilder")
    public static class Parcelas implements Serializable {

        @JsonProperty("Parcela")
        List<Parcela> parcelas;

        @Value
        @JsonDeserialize(builder = Parcela.JacksonBuilder.class)
        @Builder(builderClassName = "JacksonBuilder")
        public static class Parcela implements Serializable {

            @JsonProperty("ValorParcela")
            BigDecimal valor;

            @JsonProperty("DataVencimento")
            LocalDate dataVencimento;

            @JsonPOJOBuilder(withPrefix = "")
            public static class JacksonBuilder {

            }
        }

        @JsonPOJOBuilder(withPrefix = "")
        public static class JacksonBuilder {

        }
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }
}