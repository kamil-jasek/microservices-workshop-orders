package com.mycompany.application.exception;

public class DomainIllegalArgumentException extends RuntimeException {
    public DomainIllegalArgumentException(String message) {
        super(message);
    }
}
