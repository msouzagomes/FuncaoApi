package br.com.calcred.api.integration.account;

import br.com.calcred.api.integration.account.dto.AccountDetailPierlabsDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.MediaType;
import java.util.Optional;

public interface AccountIntegration {

    @GetMapping(value="${api.egress.account.v2.find-detail}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Optional<AccountDetailPierlabsDTO> findAccountDetailById(@PathVariable("id") Long accountId);
}