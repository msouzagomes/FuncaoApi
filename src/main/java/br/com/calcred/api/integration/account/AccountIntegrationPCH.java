package br.com.calcred.api.integration.account;

import br.com.calcred.api.integration.config.FeignIntegrationConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "AccountIntegrationPCH", qualifier = "PCH",
        url = "${api.egress.host}${api.egress.platform.pch}",
        configuration = {FeignIntegrationConfig.class})
public interface AccountIntegrationPCH extends AccountIntegration {
}