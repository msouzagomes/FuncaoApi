
package br.com.calcred.api.integration.account;

import br.com.calcred.api.integration.config.FeignIntegrationConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "AccountIntegrationPCPL", qualifier = "PCPL",
        url = "${api.egress.host}${api.egress.platform.pcpl}",
        configuration = {FeignIntegrationConfig.class})
public interface AccountIntegrationPCPL extends AccountIntegration {
}