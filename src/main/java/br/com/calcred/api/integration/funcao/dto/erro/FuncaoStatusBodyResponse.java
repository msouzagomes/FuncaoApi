package br.com.calcred.api.integration.funcao.dto.erro;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@JsonDeserialize(builder = FuncaoStatusBodyResponse.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class FuncaoStatusBodyResponse {

    @JsonProperty("STATUS")
    FuncaoStatusBody statusBody;

}