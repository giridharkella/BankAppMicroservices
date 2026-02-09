package org.gsk.bankapp.loans.controller;


import corg.gsk.bankapp.loans.api.generated.model.LoansDto;
import corg.gsk.bankapp.loans.api.generated.model.ResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.gsk.bankapp.loans.api.generated.api.LoansApi;
import org.gsk.bankapp.loans.constants.LoansConstants;
import org.gsk.bankapp.loans.service.LoansService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Validated
public class LoansController implements LoansApi {

    private LoansService loansService;


    public ResponseEntity<ResponseDto> createLoan(@RequestParam

                                                  String mobileNumber) {
        loansService.createLoan(mobileNumber);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatusCode(LoansConstants.STATUS_201);
        responseDto.setStatusMsg(LoansConstants.MESSAGE_201);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }


    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam

                                                     String mobileNumber) {
        LoansDto loansDto = loansService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }


    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {
        boolean isUpdated = loansService.updateLoan(loansDto);
        if (isUpdated) {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatusCode(LoansConstants.STATUS_200);
            responseDto.setStatusMsg(LoansConstants.MESSAGE_200);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseDto);
        } else {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatusCode(LoansConstants.STATUS_417);
            responseDto.setStatusMsg(LoansConstants.MESSAGE_417_UPDATE);
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(responseDto);
        }
    }


    public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam

                                                         String mobileNumber) {
        boolean isDeleted = loansService.deleteLoan(mobileNumber);
        if (isDeleted) {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatusCode(LoansConstants.STATUS_200);
            responseDto.setStatusMsg(LoansConstants.MESSAGE_200);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseDto);
        } else {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatusCode(LoansConstants.STATUS_417);
            responseDto.setStatusMsg(LoansConstants.MESSAGE_417_DELETE);
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(responseDto);
        }
    }

}
