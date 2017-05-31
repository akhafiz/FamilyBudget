package ru.akhafiz.dao.api;

import ru.akhafiz.dao.exceptions.FbDaoException;
import ru.akhafiz.domain.model.BaseEntity;

import java.sql.Connection;
import java.util.List;

/**
 * CRUD methods
 *
 * @param <T> type entity
 * @see BaseEntity
 * @author akhafiz
 */
public interface Dao<T extends BaseEntity> {

    /**
     * @param entity entity
     * @param connection
     * @return saved entity
     * @throws FbDaoException
     */
    T createOrUpdate(T entity, Connection connection) throws FbDaoException;

    /**
     *
     * @param id id entity for delete
     * @param connection
     * @throws FbDaoException
     */
    void deleteById(Long id, Connection connection) throws FbDaoException;

    /**
     * @param id entity id
     * @param connection
     * @return found entity
     * @throws FbDaoException
     */
    T findById(Long id, Connection connection) throws FbDaoException;

    /**
     * @return list of found entities
     * @throws FbDaoException
     * @param connection
     */
    List<T> findAll(Connection connection) throws FbDaoException;
}
