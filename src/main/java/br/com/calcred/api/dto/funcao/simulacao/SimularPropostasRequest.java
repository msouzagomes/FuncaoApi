package br.com.calcred.api.dto.funcao.simulacao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = SimularPropostasRequest.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class SimularPropostasRequest implements Serializable {

    @NotNull(message = "Quantidade máxima de parcelas deve ser informada.")
    Integer quantidadeMaximaParcelas;

    @NotNull(message = "Quantidade mínima de parcelas deve ser informada.")
    Integer quantidadeMinimaParcelas;

    @NotBlank(message = "Código do produto deve ser informado.")
    String codigoProduto;

    @NotBlank(message = "Código da tabela de financiamento deve ser informado.")
    String codigoTabela;

    @NotNull(message = "Valor financiado deve ser informado.")
    BigDecimal valorFinanciado;

    @NotNull(message = "Data base deve ser informada.")
    OffsetDateTime dataBase;

    @NotNull(message = "Data primeiro vencimento deve ser informada.")
    LocalDate dataPrimeiroVencimento;

    @NotNull(message = "Tipo do primeiro vencimento deve ser informado.")
    TipoPrimeiroVencimento tipoPrimeiroVencimento;

    @NotNull(message = "Regime de tribuição deve ser informado.")
    RegimeDeTributacao regimeDeTributacao;

    String correspondenteBancario;

    @NotBlank(message = "Origem 4 (código da loja) deve ser informada.")
    String codigoLoja;

    String gerente;

    @NotBlank(message = "Origem 30 (promotor da loja) deve ser informada.")
    String promotorLoja;

    @NotNull(message = "Tipo pessoa dever ser informado.")
    TipoPessoa tipoPessoa;

    @NotBlank(message = "CPF deve ser informado.")
    String cpf;

    @NotNull(message = "Data nascimento deve ser informada.")
    OffsetDateTime dataNascimento;

    @NotNull(message = "UF deve ser informado.")
    Uf uf;

    Pne pne;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }
}