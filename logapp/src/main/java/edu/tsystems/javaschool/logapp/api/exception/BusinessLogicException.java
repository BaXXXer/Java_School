package edu.tsystems.javaschool.logapp.api.exception;

public abstract class BusinessLogicException extends RuntimeException {
    public BusinessLogicException(String message) {
        super(message);
    }

    public BusinessLogicException() {
    }

    public BusinessLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessLogicException(Throwable cause) {
        super(cause);
    }

    public BusinessLogicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
