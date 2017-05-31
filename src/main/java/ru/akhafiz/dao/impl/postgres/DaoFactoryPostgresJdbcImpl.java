package ru.akhafiz.dao.impl.postgres;

import ru.akhafiz.dao.api.*;
import ru.akhafiz.dao.exceptions.FbDaoException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @see DaoFactory
 * @author akhafiz
 */
public class DaoFactoryPostgresJdbcImpl implements DaoFactory {

    private static final Map<Class<? extends Dao>, Class<? extends BaseDaoPostgresJdbcImpl>> DAO_IMPLEMENTATION_MAP =
            new HashMap<Class<? extends Dao>, Class<? extends BaseDaoPostgresJdbcImpl>>() {
                {
                    put(UserDao.class, UserDaoPostgresJdbcImpl.class);
                    put(FamilyDao.class, FamilyDaoPostgresJdbcImpl.class);
                    put(FamilyMemberDao.class, FamilyMemberDaoPostgresJdbcImpl.class);
                    put(FamilyBudgetDao.class, FamilyBudgetDaoPostgresJdbcImpl.class);
                }
            };

    private Connection currentConnection = null;

    private DataSource dataSource;

    public DaoFactoryPostgresJdbcImpl(DaoConfiguration daoConfiguration) throws FbDaoException {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/FamilyBudgetDB");
        } catch (NamingException e) {
            throw new FbDaoException("",e);
        }
    }

    public void openConnection() throws FbDaoException {
        if (currentConnection != null) {
            throw new FbDaoException("ERROR DAO: current connection is already open");
        }
        try {
            currentConnection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new FbDaoException("ERROR DAO: can not be open connection",e);
        }
    }

    @Override
    public Connection getCurrentConnection() throws FbDaoException {
        if (currentConnection == null) {
            throw new FbDaoException("ERROR DAO: current connection is closed");
        }

        return currentConnection;
    }

    public void closeConnection() throws FbDaoException {
        if (currentConnection != null) {
            try {
                currentConnection.close();
                currentConnection = null;
            } catch (SQLException e) {
                throw new FbDaoException("ERROR DAO: can not be closed connection");
            }
        }
    }

    public void beginTransaction() throws FbDaoException {
        if (currentConnection == null) {
            throw new FbDaoException("ERROR DAO: current connection is closed");
        }

        try {
            currentConnection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new FbDaoException("ERROR DAO: can not be begin new transaction",e);
        }
    }

    public void endTransaction() throws FbDaoException {
        if (currentConnection == null) {
            throw new FbDaoException("ERROR DAO: current connection is closed");
        }

        try {
            if (currentConnection.getAutoCommit()) {
                throw new FbDaoException("ERROR DAO: current transaction is closed");
            }
            currentConnection.commit();
        } catch (SQLException e) {
            throw new FbDaoException("ERROR DAO: error commit",e);
        }

    }

    public void abortTransaction() throws FbDaoException {
        if (currentConnection == null) {
            throw new FbDaoException("ERROR DAO: current connection is closed");
        }

        try {
            if (currentConnection.getAutoCommit()) {
                throw new FbDaoException("ERROR DAO: current transaction is closed");
            }
            currentConnection.rollback();
        } catch (SQLException e) {
            throw new FbDaoException("ERROR DAO: error rollback",e);
        }
    }

    @Override
    public <T extends Dao> T createDao(Class<T> daoClass) throws FbDaoException {
        Class<? extends BaseDaoPostgresJdbcImpl> implClass = DAO_IMPLEMENTATION_MAP.get(daoClass);
        if (implClass == null) {
            throw new FbDaoException("");
        }
        try {
            return (T) implClass.newInstance();
        } catch (Exception e) {
            throw new FbDaoException(e);
        }
    }
}
