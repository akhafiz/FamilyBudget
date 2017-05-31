package ru.akhafiz.dao.impl.postgres;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.akhafiz.dao.api.Dao;
import ru.akhafiz.dao.exceptions.FbDaoException;
import ru.akhafiz.dao.exceptions.FbMetaDataException;
import ru.akhafiz.dao.metadata.MetaDataFactory;
import ru.akhafiz.dao.metadata.MetaDataPersistClass;
import ru.akhafiz.dao.metadata.MetaDataPersistField;
import ru.akhafiz.domain.model.BaseEntity;

import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.*;

/**
 * CRUD methods
 *
 * @author akhafiz
 */
abstract class BaseDaoPostgresJdbcImpl<T extends BaseEntity> implements Dao<T> {

    private static final Logger logger = LoggerFactory.getLogger(BaseDaoPostgresJdbcImpl.class);
    private static final String MESSAGE_ERR_FIND_BY_ID = "ERROR DAO: error find by id [entity: %s;id=%d]";
    private static final String MESSAGE_ERR_FIND_ALL = "ERROR DAO: error find all entities [entity: %s]";
    private static final String MESSAGE_ERR_FIND_BY_CRITERIA = "ERROR DAO: error find by criteria [entity: %s;criteria: %s]";
    private static final String MESSAGE_ERR_ENTITY_IS_NULL = "ERROR DAO: entity must be not null [entity: %s]";
    private static final String MESSAGE_ERR_ENTITY_ID_IS_NULL = "ERROR DAO: entity id must be not null [entity: %s]";
    private static final String MESSAGE_ERR_CREATE_ENTITY = "ERROR DAO: can not create entity [entity: %s]";
    private static final String MESSAGE_ERR_CALL_SETTER = "ERROR DAO: error call setter [entity: %s;setter: %s]";
    private static final String MESSAGE_ERR_GET_COLUMN_VALUE = "ERROR DAO: error get value column %s [entity: %s]";
    private static final String MESSAGE_ERR_UPDATE_ENTITY = "ERROR DAO: error update entity [entity: %s;id=%d]";
    private static final String MESSAGE_ERR_SAVE_NEW_ENTITY = "ERROR DAO: error save entity [entity: %s]";
    private static final String MESSAGE_ERR_GENERATE_PK = "ERROR DAO: not found generated primary key [entity: %s;]";
    private static final String MESSAGE_ERR_DELETE_ENTITY = "ERROR DAO: error delete entity [entity: %s;id=%d]";
    private static final String MESSAGE_ERR_NOT_FOUND_COLUMN_BY_PARAM = "ERROR DAO: not found column for parameter [entity: %s;parameter: %s]";
    private static final String MESSAGE_ERR_NOT_FOUND_METADATA_FIELD = "ERROR DAO: not found meta data by column [entity: %s;column: %s]";
    private static final String MESSAGE_ERR_NOT_UNIQUE_RESULT = "ERROR DAO: not unique result";

    private Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    private MetaDataPersistClass<T> metaDataPersistClass = MetaDataFactory.getInstance().getMetaDataPersistClass(persistentClass);


    protected String getMainAlias() {
        return metaDataPersistClass.getMetaDataTable().getAlias();
    }

    private String generateQueryBaseSelect() {
        StringBuilder queryBuilder = new StringBuilder("SELECT ");

        Iterator<String> it = metaDataPersistClass.getMetaDataTable().getColumns().iterator();
        while (it.hasNext()) {
            String columnName = it.next();
            queryBuilder.append(getMainAlias()).append(".").append(columnName).append(it.hasNext() ? ", " : " ");
        }

        queryBuilder.append("FROM ").append(metaDataPersistClass.getMetaDataTable().getTableName()).append(" ").append(getMainAlias()).append(" \n");

        return queryBuilder.toString();
    }

    private String generateQuerySelectById() {
        return generateQueryBaseSelect() + "WHERE " +
                getMainAlias() + "." + metaDataPersistClass.getMetaDataTable().getNamePK() + " = ? ";
    }

    private String generateQueryFindByCriteria(SearchCriteria criteria) throws FbDaoException {
        StringBuilder queryBuilder = new StringBuilder(generateQueryBaseSelect());
        if (!criteria.getParameters().isEmpty()) {
            queryBuilder.append("WHERE ");
            for (String keyParam : criteria.getParameters().keySet()) {
                try {
                    queryBuilder.append(getMainAlias()).append(".").append(metaDataPersistClass.getColumnByFieldName(keyParam)).append(" = ? ");
                } catch (FbMetaDataException e) {
                    logger.error(String.format(MESSAGE_ERR_NOT_FOUND_COLUMN_BY_PARAM,persistentClass.getName(),keyParam),e);
                    throw new FbDaoException(String.format(MESSAGE_ERR_NOT_FOUND_COLUMN_BY_PARAM,persistentClass.getName(),keyParam),e);
                }
            }
        }

        return queryBuilder.toString();
    }

    private String generateQueryInsert() {
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO ").append(metaDataPersistClass.getMetaDataTable().getTableName()).append(" ( ");
        StringBuilder valuesClause = new StringBuilder(" ) VALUES (");

        Iterator<String> it = metaDataPersistClass.getMetaDataTable().getColumns().iterator();
        while (it.hasNext()) {
            String columnName = it.next();
            if (!columnName.equals(metaDataPersistClass.getMetaDataTable().getNamePK())) {
                queryBuilder.append(columnName);
                valuesClause.append("?");
                if (it.hasNext()) {
                    queryBuilder.append(", ");
                    valuesClause.append(", ");
                }
            }
        }

        queryBuilder.append(valuesClause.append(" )"));

        return queryBuilder.toString();
    }

    private String generateQueryUpdate() {
        StringBuilder queryBuilder = new StringBuilder().append("UPDATE ").append(metaDataPersistClass.getMetaDataTable().getTableName()).append(" SET ");

        Iterator<String> it = metaDataPersistClass.getMetaDataTable().getColumns().iterator();
        while (it.hasNext()) {
            String columnName = it.next();
            if (!columnName.equals(metaDataPersistClass.getMetaDataTable().getNamePK())) {
                queryBuilder.append(columnName).append(" = ? ");
                if (it.hasNext()) {
                    queryBuilder.append(", ");
                }
            }
        }

        queryBuilder.append("WHERE ").append(metaDataPersistClass.getMetaDataTable().getNamePK()).append(" = ? ");

        return queryBuilder.toString();
    }

    private String generateQueryDeleteById() {
        return "DELETE FROM " + metaDataPersistClass.getMetaDataTable().getTableName() +
                " WHERE " + metaDataPersistClass.getMetaDataTable().getNamePK() + " = ?";
    }

    private void fillStatement(PreparedStatement statement,List<Object> values) throws SQLException{
        int i = 0;
        for (Object value : values) {
            statement.setObject(++i,value);
        }
    }

    private List<Object> getValueColumnsWithoutPK(T entity) throws FbDaoException {
        List<Object> values = new ArrayList<>();
        for (String columnName : metaDataPersistClass.getMetaDataTable().getColumns()) {
            if (!columnName.equals(metaDataPersistClass.getMetaDataTable().getNamePK())) {
                try {
                    values.add(metaDataPersistClass.getMetaDataFieldByColumnName(columnName).getGetter().invoke(entity));
                } catch (Exception e) {
                    logger.error(String.format(MESSAGE_ERR_GET_COLUMN_VALUE,columnName,persistentClass.getName()),e);
                    throw new FbDaoException(String.format(MESSAGE_ERR_GET_COLUMN_VALUE,columnName,persistentClass.getName()),e);
                }
            }
        }

        return values;
    }

    private void fillStatementForUpdate(PreparedStatement updateStatement, T entity) throws SQLException,FbDaoException {
        List<Object> values = getValueColumnsWithoutPK(entity);
        values.add(entity.getId());
        fillStatement(updateStatement,values);
    }

    private void fillStatementForInsert(PreparedStatement insertStatement, T entity) throws SQLException,FbDaoException {
        fillStatement(insertStatement, getValueColumnsWithoutPK(entity));
    }

    private void fillStatementForFindByCriteria(PreparedStatement statement, SearchCriteria criteria) throws SQLException,FbDaoException {
        fillStatement(statement,new ArrayList<>(criteria.getParameters().values()));
    }

    private List<T> transformResultSet(ResultSet resultSet) throws FbDaoException,SQLException {
        ArrayList<T> entityList = new ArrayList<>();
        int columnCount = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            T entity = createEntity();
            for (int i = 1; i <= columnCount; i++) {
                MetaDataPersistField metaDataPersistField;
                try {
                    metaDataPersistField = metaDataPersistClass.getMetaDataFieldByColumnName(resultSet.getMetaData().getColumnName(i));
                } catch (FbMetaDataException e) {
                    logger.error(String.format(MESSAGE_ERR_NOT_FOUND_METADATA_FIELD,persistentClass.getName(),resultSet.getMetaData().getColumnName(i)),e);
                    throw new FbDaoException(String.format(MESSAGE_ERR_NOT_FOUND_METADATA_FIELD,persistentClass.getName(),resultSet.getMetaData().getColumnName(i)),e);
                }
                Object value = resultSet.getObject(i);
                try {
                    metaDataPersistField.getSetter().invoke(entity,value);
                } catch (Exception e) {
                    logger.error(String.format(MESSAGE_ERR_CALL_SETTER,persistentClass.getName(),metaDataPersistField.getSetter().getName()),e);
                    throw new FbDaoException(String.format(MESSAGE_ERR_CALL_SETTER,persistentClass.getName(),metaDataPersistField.getSetter().getName()),e);
                }
            }
            entityList.add(entity);
        }

        return entityList;
    }

    private T createEntity() throws FbDaoException {
        try {
            return persistentClass.newInstance();
        } catch (Exception e) {
            logger.error(String.format(MESSAGE_ERR_CREATE_ENTITY,persistentClass.getName()),e);
            throw new FbDaoException(String.format(MESSAGE_ERR_CREATE_ENTITY,persistentClass.getName()),e);
        }
    }

    private T update(T entity, Connection connection) throws FbDaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(generateQueryUpdate())) {
            fillStatementForUpdate(preparedStatement,entity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format(MESSAGE_ERR_UPDATE_ENTITY,persistentClass.getName(),entity.getId()),e);
            throw new FbDaoException(String.format(MESSAGE_ERR_UPDATE_ENTITY,persistentClass.getName(),entity.getId()),e);
        }

        return entity;
    }

    private T save(T entity, Connection connection) throws FbDaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(generateQueryInsert(), Statement.RETURN_GENERATED_KEYS)) {
            fillStatementForInsert(preparedStatement,entity);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            } else {
                logger.error(String.format(MESSAGE_ERR_GENERATE_PK,persistentClass.getName()));
                throw new FbDaoException(String.format(MESSAGE_ERR_GENERATE_PK,persistentClass.getName()));
            }
        } catch (SQLException e) {
            logger.error(String.format(MESSAGE_ERR_SAVE_NEW_ENTITY,persistentClass.getName()),e);
            throw new FbDaoException(String.format(MESSAGE_ERR_SAVE_NEW_ENTITY,persistentClass.getName()),e);
        }

        return entity;
    }


    @Override
    public T createOrUpdate(T entity, Connection connection) throws FbDaoException {
        if (entity == null) {
            logger.error(String.format(MESSAGE_ERR_ENTITY_IS_NULL,persistentClass.getName()));
            throw new FbDaoException(String.format(MESSAGE_ERR_ENTITY_IS_NULL,persistentClass.getName()));
        }

        return entity.getId() == null ? save(entity, connection) : update(entity, connection);
    }

    @Override
    public void deleteById(Long id, Connection connection) throws FbDaoException {
        if (id == null) {
            logger.error(String.format(MESSAGE_ERR_ENTITY_ID_IS_NULL,persistentClass.getName()));
            throw new FbDaoException(String.format(MESSAGE_ERR_ENTITY_ID_IS_NULL,persistentClass.getName()));
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(generateQueryDeleteById())) {
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format(MESSAGE_ERR_DELETE_ENTITY,persistentClass.getName(),id),e);
            throw new FbDaoException(String.format(MESSAGE_ERR_DELETE_ENTITY,persistentClass.getName(),id),e);
        }
    }

    @Override
    public T findById(Long id, Connection connection) throws FbDaoException {
        if (id == null) {
            logger.error(MESSAGE_ERR_ENTITY_ID_IS_NULL);
            throw new FbDaoException(MESSAGE_ERR_ENTITY_ID_IS_NULL);
        }
        try (PreparedStatement statement = connection.prepareStatement(generateQuerySelectById())) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<T> resultList = transformResultSet(resultSet);
                if (!resultList.isEmpty()) {
                    return resultList.iterator().next();
                }
            }
        } catch (SQLException e) {
            logger.error(String.format(MESSAGE_ERR_FIND_BY_ID,persistentClass.getName(),id),e);
            throw new FbDaoException(String.format(MESSAGE_ERR_FIND_BY_ID,persistentClass.getName(),id),e);
        }

        return null;
    }

    @Override
    public List<T> findAll(Connection connection) throws FbDaoException {
        try (PreparedStatement statement = connection.prepareStatement(generateQueryBaseSelect())) {
            try (ResultSet resultSet = statement.executeQuery()) {
                return transformResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error(String.format(MESSAGE_ERR_FIND_ALL,persistentClass.getName()),e);
            throw new FbDaoException(String.format(MESSAGE_ERR_FIND_ALL,persistentClass.getName()),e);
        }
    }

    private List<T> findByCriteria(SearchCriteria criteria,Connection connection) throws FbDaoException {
        try (PreparedStatement statement = connection.prepareStatement(generateQueryFindByCriteria(criteria))) {
            fillStatementForFindByCriteria(statement,criteria);
            try (ResultSet resultSet = statement.executeQuery()) {
                return transformResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error(String.format(MESSAGE_ERR_FIND_BY_CRITERIA,persistentClass.getName(),criteria.toString()),e);
            throw new FbDaoException(String.format(MESSAGE_ERR_FIND_BY_CRITERIA,persistentClass.getName(),criteria.toString()),e);
        }
    }

    SearchCriteria createSearchCriteria(Connection connection) {
        return new SearchCriteria(connection);
    }

    final class SearchCriteria {

        private Map<String,Object> parameters = new HashMap<>();

        private Connection connection;

        SearchCriteria(Connection connection) {
            this.connection = connection;
        }

        void addParameter(String key, Object value) {
            parameters.put(key,value);
        }

        Map<String,Object> getParameters() {
            return parameters;
        }

        T getUniqueResult() throws FbDaoException {

            List<T> result = findByCriteria(this,connection);
            if (!result.isEmpty()) {
                if (result.size() > 1) {
                    logger.error(MESSAGE_ERR_NOT_UNIQUE_RESULT);
                    throw new FbDaoException(MESSAGE_ERR_NOT_UNIQUE_RESULT);
                }
                return result.iterator().next();
            }

            return null;
        }

        List<T> getResultList() throws FbDaoException {
            return findByCriteria(this,connection);
        }
    }
}
