package br.com.calcred.api.integration.funcao.dto.operacao;

import java.io.Serializable;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@Builder
public class ConsultarOperacoesRequest implements Serializable {

    String cpf;
}