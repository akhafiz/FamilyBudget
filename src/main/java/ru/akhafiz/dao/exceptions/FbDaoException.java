package ru.akhafiz.dao.exceptions;

/**
 * @author akhafiz
 */
public class FbDaoException  extends Exception {

    public FbDaoException(String message) {
        super(message);
    }

    public FbDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public FbDaoException(Throwable cause) {
        super(cause);
    }
}
