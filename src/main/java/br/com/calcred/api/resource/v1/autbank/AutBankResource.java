package br.com.calcred.api.resource.v1.autbank;


import autbank.ListarTipoRenda;
import autbank.ListarTipoRendaResponse;
import br.com.calcred.api.service.AutBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/autbank")
public class AutBankResource {

    private final AutBankService autBankService;

    @PostMapping
    public ListarTipoRendaResponse listarTipoRenda(@RequestBody ListarTipoRenda listarTipoRenda){
        return autBankService.listarTipoRenda(listarTipoRenda);
    }

}
