package br.com.calcred.api.integration.funcao.dto.simulacao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@Builder
public class SimularPropostasRequest implements Serializable {

    CondicaoCredito condicaoCredito;
    Origens origens;

    @Value
    @Builder
    public static class Origens implements Serializable {

        String origem3;
        String origem4;
        String origem5;
        String origem3O;
    }

    @Value
    @Builder
    public static class CondicaoCredito implements Serializable {

        String codigoProduto;
        String codigoTabela;
        Parcelas parcelas;
        BigDecimal valorFinanciado;
        LocalDateTime dataBase;
        LocalDateTime dataPrimeiroVencimento;
        String tipoPrimeiroVencimento;
        DadoCadastral dadoCadastral;

        @Value
        @Builder
        public static class DadoCadastral implements Serializable {

            String tipoPessoa;
            String CPFCNPJ;
            String regimeDeTributacao;
            LocalDateTime dataNascimento;
            String pne;
            String uf;
        }

        @Value
        @Builder
        public static class Parcelas implements Serializable {

            List<Parcela> parcela;

            @Value
            @Builder
            public static class Parcela implements Serializable {

                Integer quantidadeParcelas;
            }
        }
    }
}