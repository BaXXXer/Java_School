package edu.tsystems.javaschool.logapp.api.exceptions;

public class DuplicateEntityException extends BusinessLogicException {
    public DuplicateEntityException(String message) {
        super(message);
    }
}
