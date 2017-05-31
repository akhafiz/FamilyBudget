package ru.akhafiz.dao.impl.postgres;

import ru.akhafiz.dao.api.UserDao;
import ru.akhafiz.dao.exceptions.FbDaoException;
import ru.akhafiz.domain.model.User;

import java.sql.Connection;

/**
 * <p></p>
 *
 * @author akhafiz
 */
class UserDaoPostgresJdbcImpl extends BaseDaoPostgresJdbcImpl<User> implements UserDao {

    @Override
    public User findByLogin(String login, Connection connection) throws FbDaoException {
        SearchCriteria criteria = createSearchCriteria(connection);
        criteria.addParameter("login",login);
        return criteria.getUniqueResult();
    }

    @Override
    public User findByEmail(String email, Connection connection) throws FbDaoException {
        SearchCriteria criteria = createSearchCriteria(connection);
        criteria.addParameter("email",email);
        return criteria.getUniqueResult();
    }
}
