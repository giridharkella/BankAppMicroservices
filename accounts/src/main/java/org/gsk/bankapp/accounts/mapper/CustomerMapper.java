package org.gsk.bankapp.accounts.mapper;


import corg.gsk.bankapp.accounts.api.generated.model.CustomerDto;
import org.gsk.bankapp.accounts.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    public CustomerDto mapToCustomerDto(Customer customer);

    public Customer mapToCustomer(CustomerDto customerDto);

}
