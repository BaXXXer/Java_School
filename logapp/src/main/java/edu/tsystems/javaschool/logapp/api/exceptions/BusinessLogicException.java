package edu.tsystems.javaschool.logapp.api.exceptions;

public abstract class BusinessLogicException extends Exception {
    public BusinessLogicException(String message) {
        super(message);
    }
}
