package org.gsk.bankapp.accounts.controller;

import corg.gsk.bankapp.accounts.api.generated.model.AccountsContactInfoDto;
import corg.gsk.bankapp.accounts.api.generated.model.CustomerDetailsDto;
import corg.gsk.bankapp.accounts.api.generated.model.CustomerDto;
import corg.gsk.bankapp.accounts.api.generated.model.ResponseDto;
import org.gsk.bankapp.accounts.api.generated.api.AccountsApi;
import org.gsk.bankapp.accounts.api.generated.api.CustomerApi;
import org.gsk.bankapp.accounts.config.AccountsContactInfo;
import org.gsk.bankapp.accounts.constants.AccountsConstants;
import org.gsk.bankapp.accounts.service.AccountsService;
import org.gsk.bankapp.accounts.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @Override
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
            String mobileNumber) {
        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
    }


}

