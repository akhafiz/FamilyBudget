package ru.akhafiz.dao.exceptions;

/**
 * <p></p>
 *
 * @author akhafiz
 */
public class FbMetaDataException extends RuntimeException {
    public FbMetaDataException(String message) {
        super(message);
    }

    public FbMetaDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public FbMetaDataException(Throwable cause) {
        super(cause);
    }

}
