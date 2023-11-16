package com.raouf.gestionabsence.exception;

public class CINAlreadyExistsException extends RuntimeException {
    public CINAlreadyExistsException(String message) {
        super(message);
    }
}