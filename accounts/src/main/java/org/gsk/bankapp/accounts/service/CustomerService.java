package org.gsk.bankapp.accounts.service;

import corg.gsk.bankapp.accounts.api.generated.model.CardsDto;
import corg.gsk.bankapp.accounts.api.generated.model.CustomerDetailsDto;
import corg.gsk.bankapp.accounts.api.generated.model.CustomerDto;
import corg.gsk.bankapp.accounts.api.generated.model.LoansDto;
import lombok.AllArgsConstructor;
import org.gsk.bankapp.accounts.entity.Accounts;
import org.gsk.bankapp.accounts.entity.Customer;
import org.gsk.bankapp.accounts.exception.ResourceNotFoundException;
import org.gsk.bankapp.accounts.mapper.AccountsMapper;
import org.gsk.bankapp.accounts.mapper.CustomerMapper;
import org.gsk.bankapp.accounts.repository.AccountsRepository;
import org.gsk.bankapp.accounts.repository.CustomerRepository;
import org.gsk.bankapp.accounts.service.client.CardsServiceClient;
import org.gsk.bankapp.accounts.service.client.LoansServiceClient;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsServiceClient cardsServiceClient;
    private LoansServiceClient loansServiceClient;
    private CustomerMapper customerMapper;
    private AccountsMapper accountsMapper;

    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = customerMapper.mapToCustomerDto(customer);
        customerDto.setAccountsDto(accountsMapper.mapToAccountsDto(accounts));

        CardsDto cardsDto = cardsServiceClient.fetchCardDetails(mobileNumber).getBody();
        LoansDto loansDto = loansServiceClient.fetchLoanDetails(mobileNumber).getBody();

        customerDetailsDto.setCustomerDto(customerDto);
        customerDetailsDto.setCardsDto(cardsDto);
        customerDetailsDto.setLoansDto(loansDto);

        return customerDetailsDto;
    }
}
