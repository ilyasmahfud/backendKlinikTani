package com.company.klinikTani.exception;

public class AuthenticateFailedException extends RuntimeException {
    public AuthenticateFailedException(String message) {
        super(message);
    }
}
