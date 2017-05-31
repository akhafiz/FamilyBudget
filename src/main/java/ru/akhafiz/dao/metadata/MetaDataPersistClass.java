package ru.akhafiz.dao.metadata;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.akhafiz.dao.exceptions.FbMetaDataException;
import ru.akhafiz.domain.model.BaseEntity;
import ru.akhafiz.domain.model.annotations.Column;
import ru.akhafiz.domain.model.annotations.PrimaryKey;
import ru.akhafiz.domain.model.annotations.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * <p></p>
 *
 * @author akhafiz
 */
public class MetaDataPersistClass<T extends BaseEntity> {

    private static final Logger logger = LoggerFactory.getLogger(MetaDataPersistClass.class);
    private static final String MESSAGE_ERR_NOT_FOUND_ANNOTATION = "METADATA ERROR: not found annotation %s for persist class %s";
    private static final String MESSAGE_ERR_FOUND_MORE_ONE_PK = "METADATA ERROR: found more one primary key for entity %s";
    private static final String MESSAGE_ERR_EMPTY_COLUMN_NAME = "METADATA ERROR: column name must be not empty [entity: %s;field: %s]";
    private static final String MESSAGE_ERR_NOT_FOUND_SETTER = "METADATA ERROR: not found setter %s [entity: %s;field: %s]";
    private static final String MESSAGE_ERR_NOT_FOUND_GETTER = "METADATA ERROR: not found getter %s [entity: %s;field: %s]";
    private static final String MESSAGE_ERR_RETURNED_TYPE_NOT_EQUALS = "METADATA ERROR: ReturnType not equals %s [entity: %s;method: %s]";
    private static final String MESSAGE_ERR_NOT_FOUND_MD_BY_COLUMN_NAME = "METADATA ERROR: not found metadata by column %s";
    private static final String MESSAGE_ERR_NOT_FOUND_COLUMN_BY_FIELD_NAME = "METADATA ERROR: not found column by field name %s";

    private Class<T> persistClass;
    private MetaDataTable metaDataTable;
    private Map<String,MetaDataPersistField> columnNameMDPersistFieldMap = new HashMap<>();
    private Map<String,String> fieldColumnMap = new HashMap<>();


    public MetaDataPersistClass(Class<T> persistClass) throws FbMetaDataException {
        this.persistClass = persistClass;
        initialize();
    }

    private void initialize() throws FbMetaDataException {

        Table tableAnnotation = persistClass.getAnnotation(Table.class);
        if (tableAnnotation == null || Strings.isNullOrEmpty(tableAnnotation.name())) {
            logger.error(String.format(MESSAGE_ERR_NOT_FOUND_ANNOTATION,Table.class.getName(), persistClass.getName()));
            throw new FbMetaDataException(String.format(MESSAGE_ERR_NOT_FOUND_ANNOTATION,Table.class.getName(), persistClass.getName()));
        }
        String tableName = tableAnnotation.name();
        String tableAlias = Strings.isNullOrEmpty(tableAnnotation.alias()) ? persistClass.getSimpleName() : tableAnnotation.alias();

        String primaryKey = "";
        for (Field persistField : searchPersistFields()) {
            String columnName = persistField.getAnnotation(Column.class).name();
            if (Strings.isNullOrEmpty(columnName)) {
                logger.error(String.format(MESSAGE_ERR_EMPTY_COLUMN_NAME, persistClass.getName(),persistField.getName()));
                throw new FbMetaDataException(String.format(MESSAGE_ERR_EMPTY_COLUMN_NAME,persistClass.getName(),persistField.getName()));
            }

            if (persistField.getAnnotation(PrimaryKey.class) != null) {
                if (!Strings.isNullOrEmpty(primaryKey)) {
                    logger.error(String.format(MESSAGE_ERR_FOUND_MORE_ONE_PK, persistClass.getName()));
                    throw new FbMetaDataException(String.format(MESSAGE_ERR_FOUND_MORE_ONE_PK, persistClass.getName()));
                }
                primaryKey = columnName;
            }

            columnNameMDPersistFieldMap.put(columnName, new MetaDataPersistField(
                    persistField.getName(),
                    persistField.getType(),
                    searchGetterForField(persistField),
                    searchSetterForField(persistField)));
            fieldColumnMap.put(persistField.getName(),columnName);
        }

        if (Strings.isNullOrEmpty(primaryKey)) {
            logger.error(String.format(MESSAGE_ERR_NOT_FOUND_ANNOTATION,PrimaryKey.class.getName(), persistClass.getName()));
            throw new FbMetaDataException(String.format(MESSAGE_ERR_NOT_FOUND_ANNOTATION,PrimaryKey.class.getName(), persistClass.getName()));
        }

        metaDataTable = new MetaDataTable(tableName,tableAlias,primaryKey,columnNameMDPersistFieldMap.keySet());
    }

    private Method searchGetterForField(Field field) throws FbMetaDataException {
        String getterName = "get" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
        try {
            Method getter = persistClass.getMethod(getterName);
            if (getter.getReturnType() != field.getType()) {
                logger.error(String.format(MESSAGE_ERR_RETURNED_TYPE_NOT_EQUALS,field.getType().getName(),persistClass.getName(),getterName));
                throw new FbMetaDataException(String.format(MESSAGE_ERR_RETURNED_TYPE_NOT_EQUALS,field.getType().getName(),persistClass.getName(),getterName));
            }
            return getter;
        } catch (Exception e) {
            logger.error(String.format(MESSAGE_ERR_NOT_FOUND_GETTER,getterName,persistClass.getName(),field.getName()));
            throw new FbMetaDataException(String.format(MESSAGE_ERR_NOT_FOUND_GETTER,getterName,persistClass.getName(),field.getName()));
        }
    }

    private Method searchSetterForField(Field field) throws FbMetaDataException {
        String setterName = "set" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
        try {
            return persistClass.getMethod(setterName, field.getType());
        } catch (Exception e) {
            logger.error(String.format(MESSAGE_ERR_NOT_FOUND_SETTER,setterName,persistClass.getName(),field.getType()));
            throw new FbMetaDataException(String.format(MESSAGE_ERR_NOT_FOUND_SETTER,setterName,persistClass.getName(),field.getType()));
        }
    }

    private List<Field> searchPersistFields() {
        List<Field> persistFields = new ArrayList<>();

        for (Field field : persistClass.getDeclaredFields()) {
            if (Modifier.isPrivate(field.getModifiers()) && field.getAnnotation(Column.class) != null) {
                persistFields.add(field);
            }
        }

        return persistFields;
    }

    public MetaDataTable getMetaDataTable() {
        return metaDataTable;
    }

    public MetaDataPersistField getMetaDataFieldByColumnName(String columnName) throws FbMetaDataException {
        MetaDataPersistField metaData = columnNameMDPersistFieldMap.get(columnName.toUpperCase());
        if (metaData == null) {
            logger.error(String.format(MESSAGE_ERR_NOT_FOUND_MD_BY_COLUMN_NAME,columnName));
            throw new FbMetaDataException(String.format(MESSAGE_ERR_NOT_FOUND_MD_BY_COLUMN_NAME,columnName));
        }
        return metaData;
    }

    public String getColumnByFieldName(String fieldName) throws FbMetaDataException {
        String columnName = fieldColumnMap.get(fieldName);
        if (Strings.isNullOrEmpty(columnName)) {
            logger.error(String.format(MESSAGE_ERR_NOT_FOUND_COLUMN_BY_FIELD_NAME,fieldName));
            throw new FbMetaDataException(String.format(MESSAGE_ERR_NOT_FOUND_COLUMN_BY_FIELD_NAME,fieldName));
        }

        return columnName;
    }
}
