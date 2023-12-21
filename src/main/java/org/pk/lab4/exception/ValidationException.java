package org.pk.lab4.exception;

public class ValidationException extends RuntimeException {

    public ValidationException() {
        super("Validation failed - HTTP 400");
    }

}