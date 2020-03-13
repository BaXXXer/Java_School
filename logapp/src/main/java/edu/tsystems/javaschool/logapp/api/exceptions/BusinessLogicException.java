package edu.tsystems.javaschool.logapp.api.exceptions;

public abstract class BusinessLogicException extends RuntimeException {
    public BusinessLogicException(String message) {
        super(message);
    }
}
