package br.com.calcred.api.integration.autbank;


import autbank.ListarTipoRenda;
import autbank.ListarTipoRendaResponse;
import br.com.calcred.api.integration.config.FeignIntegrationSoapConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AutBankClient",
            url =  "${api.autbank.abibws.soap.urlService}",
            configuration = FeignIntegrationSoapConfig.class
        )
public interface AutBankClient {

    @PostMapping
    ListarTipoRendaResponse listarTipoRenda(@RequestBody ListarTipoRenda listarTipoRenda);

}
