package ru.akhafiz.dao.api;

import ru.akhafiz.dao.exceptions.FbDaoException;

import java.sql.Connection;

/**
 * @author akhafiz
 */
public interface DaoFactory {

    /**
     * @throws FbDaoException
     */
    void openConnection() throws FbDaoException;

    /**
     * @throws FbDaoException
     */
    void closeConnection() throws FbDaoException;

    /**
     *
     * @return current connection
     * @throws FbDaoException
     */
    Connection getCurrentConnection() throws FbDaoException;

    /**
     * @throws FbDaoException
     */
    void beginTransaction() throws FbDaoException;

    /**
     * @throws FbDaoException
     */
    void endTransaction() throws FbDaoException;

    /**
     * @throws FbDaoException
     */
    void abortTransaction() throws FbDaoException;

    /**
     *
     * @param daoClass class dao interface
     * @param <T> dao interface
     * @return object implemented interface T
     */
    <T extends Dao> T createDao(Class<T> daoClass) throws FbDaoException;

}
