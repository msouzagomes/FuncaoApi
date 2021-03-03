package br.com.calcred.api.enumeration;

import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Getter
public enum SampleSortEnum {

    FULL_NAME("ATTRIBUTE_NAME"),
    EMAIL("ATTRIBUTE_NAME"),
    CREATED("ATTRIBUTE_NAME"),
    LAST_UPDATED("ATTRIBUTE_NAME");

    private final String value;
}