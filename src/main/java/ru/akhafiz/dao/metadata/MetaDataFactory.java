package ru.akhafiz.dao.metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.akhafiz.dao.exceptions.FbMetaDataException;
import ru.akhafiz.domain.model.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author akhafiz
 */
public class MetaDataFactory {

    private static final Logger logger = LoggerFactory.getLogger(MetaDataFactory.class);
    private static final String MESSAGE_ERR_NOT_FOUND_PERSIST_CLASS = "METADATA ERROR: not found meta data for persistent class %s";

    private static MetaDataFactory instance = new MetaDataFactory();

    public static MetaDataFactory getInstance() {
        return instance;
    }

    private Map<Class<? extends BaseEntity>,MetaDataPersistClass<? extends BaseEntity>> persistClassMap = new HashMap<>();

    private MetaDataFactory() throws FbMetaDataException {
        initialize();
    }

    private void initialize() throws FbMetaDataException{
        addPersistentClass(User.class);
        addPersistentClass(Family.class);
        addPersistentClass(FamilyBudget.class);
        addPersistentClass(FamilyMember.class);
    }

    private <T extends BaseEntity> void addPersistentClass(Class<T> persistentClass) throws FbMetaDataException {
        persistClassMap.put(persistentClass,new MetaDataPersistClass<>(persistentClass));
    }

    public <T extends BaseEntity> MetaDataPersistClass<T> getMetaDataPersistClass(Class<T> persistentClass) throws FbMetaDataException {
        MetaDataPersistClass<T> metaDataPersistClass = (MetaDataPersistClass<T>) persistClassMap.get(persistentClass);
        if (metaDataPersistClass == null) {
            logger.error(String.format(MESSAGE_ERR_NOT_FOUND_PERSIST_CLASS,persistentClass.getName()));
            throw new FbMetaDataException(String.format(MESSAGE_ERR_NOT_FOUND_PERSIST_CLASS,persistentClass.getName()));
        }

        return metaDataPersistClass;
    }

}
