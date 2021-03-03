package br.com.calcred.api.service;

import br.com.calcred.api.dto.SampleDTO;
import br.com.calcred.api.enumeration.PlatformEnum;
import br.com.calcred.api.exception.BusinessException;
import br.com.calcred.api.helper.MessageHelper;
import br.com.calcred.api.integration.account.AccountIntegrationFactory;
import br.com.calcred.api.integration.account.dto.AccountDetailPierlabsDTO;
import br.com.calcred.api.integration.exception.IntegrationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.calcred.api.exception.ErrorCodeEnum.ERROR_ACCOUNT_INTEGRATION_DETAIL;
import static br.com.calcred.api.exception.ErrorCodeEnum.ERROR_ACCOUNT_NOT_FOUNT_ID;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleService {

    private final AccountIntegrationFactory accountIntegrationFactory;
    private final MessageHelper messageHelper;

    public SampleDTO findById(Long id) throws BusinessException {

        return null;

//        return sampleRepository.findById(id)
//                .map(sample -> {
//                    var accountDTO = getAccountDetailPierlabs(sample.getPlatform(), sample.getId());
//                    return SampleDTO.builder().id(accountDTO.getId()).build();
//                })
//                .orElseThrow(() -> {
//                    log.error(messageHelper.get(ERROR_ACCOUNT_NOT_FOUNT_ID));
//                    return new BusinessException(NOT_FOUND, messageHelper.get(ERROR_ACCOUNT_NOT_FOUNT_ID));
//                });
    }

    private AccountDetailPierlabsDTO getAccountDetailPierlabs(PlatformEnum platform, Long idAccountPlatform) {
        try {
            return accountIntegrationFactory.getInstance(platform.toString())
                    .findAccountDetailById(idAccountPlatform)
                    .orElseThrow(() -> {
                        log.error(messageHelper.get(ERROR_ACCOUNT_NOT_FOUNT_ID, idAccountPlatform));
                        return new BusinessException(NOT_FOUND, messageHelper.get(ERROR_ACCOUNT_NOT_FOUNT_ID, idAccountPlatform));
                    });
        } catch (IntegrationException e) {
            throw new BusinessException(e.getStatus(), messageHelper.get(ERROR_ACCOUNT_INTEGRATION_DETAIL), e);
        }
    }
}