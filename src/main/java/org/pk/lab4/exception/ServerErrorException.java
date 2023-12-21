package org.pk.lab4.exception;

public class ServerErrorException extends RuntimeException {

    public ServerErrorException() {
        super("Server is not responding - HTTP 500");
    }

}
