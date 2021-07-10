package com.company.klinikTani.exception;

public class CheckInFailedException extends RuntimeException {
    public CheckInFailedException(String message) {
        super(message);
    }
}
