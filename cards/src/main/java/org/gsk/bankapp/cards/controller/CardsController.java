package org.gsk.bankapp.cards.controller;

import corg.gsk.bankapp.accounts.api.generated.model.CardsContactInfoDto;
import corg.gsk.bankapp.accounts.api.generated.model.CardsDto;
import corg.gsk.bankapp.accounts.api.generated.model.ResponseDto;
import org.gsk.bankapp.cards.api.generated.api.CardsApi;
import org.gsk.bankapp.cards.config.CardsContactInfo;
import org.gsk.bankapp.cards.constants.CardsConstants;
import org.gsk.bankapp.cards.service.CardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CardsController implements CardsApi {

    private final CardsService cardsService;

    public CardsController(CardsService cardsService, CardsContactInfo cardsContactInfo) {
        this.cardsService = cardsService;
        this.cardsContactInfo = cardsContactInfo;
    }


    //@Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    private final CardsContactInfo cardsContactInfo;

    public ResponseEntity<ResponseDto> createCard(@RequestParam
                                                  String mobileNumber) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatusMsg(CardsConstants.MESSAGE_201);
        responseDto.setStatusCode(CardsConstants.STATUS_201);
        cardsService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }


    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam
                                                     String mobileNumber) {
        CardsDto cardsDto = cardsService.fetchCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }


    public ResponseEntity<ResponseDto> updateCardDetails(@RequestBody CardsDto cardsDto) {
        boolean isUpdated = cardsService.updateCard(cardsDto);
        if (isUpdated) {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatusMsg(CardsConstants.MESSAGE_200);
            responseDto.setStatusCode(CardsConstants.STATUS_200);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseDto);
        } else {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatusMsg(CardsConstants.MESSAGE_417_UPDATE);
            responseDto.setStatusCode(CardsConstants.STATUS_417);
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(responseDto);
        }
    }


    public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam

                                                         String mobileNumber) {
        boolean isDeleted = cardsService.deleteCard(mobileNumber);
        if (isDeleted) {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatusMsg(CardsConstants.MESSAGE_200);
            responseDto.setStatusCode(CardsConstants.STATUS_200);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseDto);
        } else {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatusMsg(CardsConstants.MESSAGE_417_DELETE);
            responseDto.setStatusCode(CardsConstants.STATUS_417);
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(responseDto);
        }
    }


    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }


    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }


    public ResponseEntity<CardsContactInfoDto> getContactInfo() {
        CardsContactInfoDto dto = new CardsContactInfoDto();
        dto.setMessage(cardsContactInfo.getMessage());
        dto.setContactDetails(cardsContactInfo.getContactDetails());
        dto.setOnCallSupport(cardsContactInfo.getOnCallSupport());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }
}
