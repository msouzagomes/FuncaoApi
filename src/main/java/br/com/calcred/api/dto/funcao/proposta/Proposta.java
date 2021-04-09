package br.com.calcred.api.dto.funcao.proposta;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import br.com.calcred.api.integration.funcao.dto.proposta.SituacaoEsteira;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@JsonDeserialize(builder = Proposta.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class Proposta {

    String numeroProposta;

    SituacaoEsteira situacaoEsteira;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }

}