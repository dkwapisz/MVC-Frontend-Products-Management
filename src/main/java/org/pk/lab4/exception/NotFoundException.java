package org.pk.lab4.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Product not found - HTTP 404");
    }

}