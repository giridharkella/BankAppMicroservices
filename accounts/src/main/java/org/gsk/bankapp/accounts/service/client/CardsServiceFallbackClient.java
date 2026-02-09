package org.gsk.bankapp.accounts.service.client;

import corg.gsk.bankapp.accounts.api.generated.model.CardsDto;
import org.springframework.http.ResponseEntity;

public class CardsServiceFallbackClient implements CardsServiceClient {
    @Override
    public ResponseEntity<CardsDto> fetchCardDetails(String mobileNumber) {
        return null;
    }
}
