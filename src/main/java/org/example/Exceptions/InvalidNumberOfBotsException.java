package org.example.Exceptions;

public class InvalidNumberOfBotsException extends IllegalArgumentException {
    public InvalidNumberOfBotsException(String message) {
        super(message);
    }
}
