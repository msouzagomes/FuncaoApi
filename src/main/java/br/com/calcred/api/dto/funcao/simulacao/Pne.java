package br.com.calcred.api.dto.funcao.simulacao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Pne {

    PNE_SIM("Sim"),
    PNE_NAO("Nao");

    String valor;
}