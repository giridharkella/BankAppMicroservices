package org.gsk.bankapp.accounts.service.client;

import corg.gsk.bankapp.accounts.api.generated.model.LoansDto;
import org.springframework.http.ResponseEntity;

public class LoansServiceFallbackClient implements LoansServiceClient{
    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String mobileNumber) {
        return null;
    }
}
