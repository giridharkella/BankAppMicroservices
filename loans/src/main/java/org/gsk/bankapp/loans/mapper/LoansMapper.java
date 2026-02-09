package org.gsk.bankapp.loans.mapper;


import corg.gsk.bankapp.loans.api.generated.model.LoansDto;
import org.gsk.bankapp.loans.entity.Loans;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LoansMapper {

    public LoansDto mapToLoansDto(Loans loans);

    public Loans mapToLoans(LoansDto loansDto);

}
