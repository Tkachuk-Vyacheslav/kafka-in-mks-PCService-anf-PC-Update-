package org.client.Exception;

public class IncorrectRequestToDbException extends RuntimeException {

    public IncorrectRequestToDbException(String message) {
        super(message);
    }
}
