package edu.tsystems.javaschool.logapp.api.exception;

public class DuplicateEntityException extends BusinessLogicException {
    public DuplicateEntityException(String message) {
        super(message);
    }
}
