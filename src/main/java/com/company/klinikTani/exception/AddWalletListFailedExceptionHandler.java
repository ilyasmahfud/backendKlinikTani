package com.company.klinikTani.exception;

import com.company.klinikTani.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AddWalletListFailedExceptionHandler {
    @ExceptionHandler(AddWalletListFailedException.class)
    public ResponseEntity<Object> handleException(AddWalletListFailedException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(false, exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
