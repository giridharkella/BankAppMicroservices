package org.gsk.bankapp.accounts.service;

import corg.gsk.bankapp.accounts.api.generated.model.AccountsDto;
import corg.gsk.bankapp.accounts.api.generated.model.CustomerDto;
import lombok.AllArgsConstructor;
import org.gsk.bankapp.accounts.constants.AccountsConstants;


import org.gsk.bankapp.accounts.entity.Accounts;
import org.gsk.bankapp.accounts.entity.Customer;
import org.gsk.bankapp.accounts.exception.CustomerAlreadyExistsException;
import org.gsk.bankapp.accounts.exception.ResourceNotFoundException;
import org.gsk.bankapp.accounts.mapper.AccountsMapper;
import org.gsk.bankapp.accounts.mapper.CustomerMapper;
import org.gsk.bankapp.accounts.repository.AccountsRepository;
import org.gsk.bankapp.accounts.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    private AccountsMapper accountsMapper;


    public void createAccount(CustomerDto customerDto) {
        Customer customer = customerMapper.mapToCustomer(customerDto);
        customerRepository.findByMobileNumber(customerDto.getMobileNumber())
                .ifPresent(existingCustomer -> {
                    throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                            + customerDto.getMobileNumber());
                });

        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(

                createNewAccount(savedCustomer));
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */

    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = customerMapper.mapToCustomerDto(customer);
        customerDto.setAccountsDto(accountsMapper.mapToAccountsDto(accounts));
        return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */

    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            accountsMapper.mapToAccounts(accountsDto);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            customerMapper.mapToCustomer(customerDto);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */

    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


}
