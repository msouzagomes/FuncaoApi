package br.com.calcred.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {

    ERROR_ACCOUNT_INTEGRATION_DECODER("error.account.integration.decoder"),
    ERROR_ACCOUNT_NOT_FOUNT_ID("error.account.integration.not-found"),
    ERROR_ACCOUNT_INTEGRATION_DETAIL("error.account.integration.detail");

    private final String messageKey;
}
