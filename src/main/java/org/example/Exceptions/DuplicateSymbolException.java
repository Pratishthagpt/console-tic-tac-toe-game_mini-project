package org.example.Exceptions;

public class DuplicateSymbolException extends IllegalArgumentException{
    public DuplicateSymbolException(String message) {
        super(message);
    }
}
