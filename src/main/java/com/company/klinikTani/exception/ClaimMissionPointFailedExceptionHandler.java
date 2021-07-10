package com.company.klinikTani.exception;

import com.company.klinikTani.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClaimMissionPointFailedExceptionHandler {
    @ExceptionHandler(ClaimMissionPointFailedException.class)
    public ResponseEntity<Object> handleException(ClaimMissionPointFailedException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(true, exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
