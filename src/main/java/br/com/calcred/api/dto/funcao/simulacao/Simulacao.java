package br.com.calcred.api.dto.funcao.simulacao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@JsonDeserialize(builder = Simulacao.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class Simulacao implements Serializable {

    Integer quantidadeParcelas;

    BigDecimal valorParcelas;

    BigDecimal valorFinanciado;

    BigDecimal valorSolicitado;

    BigDecimal jurosMensal;

    BigDecimal taxaCetMensal;

    BigDecimal taxaCetAnual;

    BigDecimal impostosOperacao;

    BigDecimal tarifaCredito;

    List<Parcela> parcelas;

    @Value
    @JsonDeserialize(builder = Parcela.JacksonBuilder.class)
    @Builder(builderClassName = "JacksonBuilder")
    public static class Parcela implements Serializable {

        Integer id;
        LocalDate vencimento;
        BigDecimal valor;

        @JsonPOJOBuilder(withPrefix = "")
        public static class JacksonBuilder {

        }
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }

}