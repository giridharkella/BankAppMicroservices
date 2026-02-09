package org.gsk.bankapp.accounts.controller;

import corg.gsk.bankapp.accounts.api.generated.model.AccountsContactInfoDto;
import corg.gsk.bankapp.accounts.api.generated.model.CustomerDto;
import corg.gsk.bankapp.accounts.api.generated.model.ResponseDto;
import io.github.resilience4j.retry.annotation.Retry;
import org.gsk.bankapp.accounts.api.generated.api.AccountsApi;
import org.gsk.bankapp.accounts.config.AccountsContactInfo;
import org.gsk.bankapp.accounts.constants.AccountsConstants;
import org.gsk.bankapp.accounts.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController implements AccountsApi {

    private final AccountsService accountsService;

    @Autowired
    private Environment environment;

    private final AccountsContactInfo accountsContactInfo;

    public AccountController(AccountsService accountsService, AccountsContactInfo accountsContactInfo) {
        this.accountsContactInfo = accountsContactInfo;
        this.accountsService = accountsService;
    }


    public ResponseEntity<ResponseDto> createAccount(CustomerDto customerDto) {
        accountsService.createAccount(customerDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.statusCode(AccountsConstants.STATUS_201);
        responseDto.setStatusMsg(AccountsConstants.MESSAGE_201);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    public ResponseEntity<CustomerDto> fetchAccountDetails(
            String mobileNumber) {
        CustomerDto customerDto = accountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }


    public ResponseEntity<ResponseDto> updateAccountDetails(CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateAccount(customerDto);
        if (isUpdated) {
            ResponseDto responseDto = new ResponseDto();
            responseDto.statusCode(AccountsConstants.STATUS_200);
            responseDto.setStatusMsg(AccountsConstants.MESSAGE_200);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseDto);
        } else {
            ResponseDto responseDto = new ResponseDto();
            responseDto.statusCode(AccountsConstants.STATUS_417);
            responseDto.setStatusMsg(AccountsConstants.MESSAGE_417_UPDATE);
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(responseDto);
        }
    }

    public ResponseEntity<ResponseDto> deleteAccountDetails(
            String mobileNumber) {
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            ResponseDto responseDto = new ResponseDto();
            responseDto.statusCode(AccountsConstants.STATUS_200);
            responseDto.setStatusMsg(AccountsConstants.MESSAGE_200);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseDto);
        } else {
            ResponseDto responseDto = new ResponseDto();
            responseDto.statusCode(AccountsConstants.STATUS_417);
            responseDto.setStatusMsg(AccountsConstants.MESSAGE_417_UPDATE);
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(responseDto);
        }
    }


    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @Retry(name = "getContactInfo", fallbackMethod = "getContactInfoFallback")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        AccountsContactInfoDto dto = new AccountsContactInfoDto();
        dto.setMessage(accountsContactInfo.getMessage());
        dto.setContactDetails(accountsContactInfo.getContactDetails());
        dto.setOnCallSupport(accountsContactInfo.getOnCallSupport());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    public ResponseEntity<AccountsContactInfoDto> getContactInfoFallback(Throwable throwable) {
        return null;
    }
}

