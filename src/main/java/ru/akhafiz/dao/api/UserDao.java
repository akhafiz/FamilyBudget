package ru.akhafiz.dao.api;

import ru.akhafiz.dao.exceptions.FbDaoException;
import ru.akhafiz.domain.model.User;

import java.sql.Connection;

/**
 * <p>User DAO</p>
 *
 * @see User
 * @see Dao
 * @author akhafiz
 */
public interface UserDao extends Dao<User> {

    User findByLogin(String login, Connection connection) throws FbDaoException;

    User findByEmail(String email,Connection connection) throws FbDaoException;

}
