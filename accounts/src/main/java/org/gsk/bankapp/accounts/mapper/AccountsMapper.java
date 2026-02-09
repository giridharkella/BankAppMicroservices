package org.gsk.bankapp.accounts.mapper;


import corg.gsk.bankapp.accounts.api.generated.model.AccountsDto;
import org.gsk.bankapp.accounts.entity.Accounts;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountsMapper {

    public AccountsDto mapToAccountsDto(Accounts accounts);

    public Accounts mapToAccounts(AccountsDto accountsDto);

}
