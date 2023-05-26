package com.solera.backendcursojava.exceptions;

public class AuthenticationOwnException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AuthenticationOwnException(String message) {
        super(message);
    }
}
