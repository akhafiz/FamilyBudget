package ru.akhafiz.domain.exceptions;

/**
 * @author akhafiz
 */
public class FbDomainException extends Exception {

    public FbDomainException() {
    }

    public FbDomainException(String message) {
        super(message);
    }

    public FbDomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public FbDomainException(Throwable cause) {
        super(cause);
    }

    public FbDomainException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
