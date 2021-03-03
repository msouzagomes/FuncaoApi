package br.com.calcred.api.integration.account;

public interface AccountIntegrationFactory {

   AccountIntegration getInstance(String platform);
}
