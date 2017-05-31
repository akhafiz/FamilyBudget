package ru.akhafiz.domain.convertors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.akhafiz.domain.dto.UserDto;
import ru.akhafiz.domain.exceptions.FbDomainException;
import ru.akhafiz.domain.model.BaseEntity;
import ru.akhafiz.domain.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author akhafiz
 */
public class ConverterManager {

    private static final Logger logger = LoggerFactory.getLogger(ConverterManager.class);
    private static final String MESSAGE_ERR_NOT_FOUND_CONVERTER = "ERROR: not found converter [entityClass: %s;dtoClass: %s]";

    private Map<Key,Converter> keyConverterMap = new HashMap<>();

    public ConverterManager() {
        initialize();
    }

    private void initialize() {
        addConverter(User.class, UserDto.class,new SimpleUserConverter());
    }

    private <E extends BaseEntity,D> void addConverter(Class<E> entityClass, Class<D> dtoClass, Converter<E,D> converter) {
        keyConverterMap.put(new Key(entityClass,dtoClass),converter);
    }

    private <E extends BaseEntity,D> Converter<E,D> findConverter(E entity, D dto) throws FbDomainException {
        Converter<E,D> converter = keyConverterMap.get(new Key(entity.getClass(), dto.getClass()));
        if (converter == null) {
            logger.error(String.format(MESSAGE_ERR_NOT_FOUND_CONVERTER,entity.getClass().getName(),dto.getClass().getName()));
            throw new FbDomainException(String.format(MESSAGE_ERR_NOT_FOUND_CONVERTER,entity.getClass().getName(),dto.getClass().getName()));
        }
        return converter;
    }

    public <E extends BaseEntity,D> E convertDtoToEntity(E entity,D dto) throws FbDomainException {
        return findConverter(entity,dto).convertDtoToEntity(entity,dto);
    }

    public <E extends BaseEntity,D> D convertEntityToDto(E entity,D dto) throws FbDomainException {
        return findConverter(entity,dto).convertEntityToDto(entity,dto);
    }

    private class Key {
        private Class<? extends BaseEntity> entityClass;

        private Class<?> dtoClass;

        Key(Class<? extends BaseEntity> entityClass, Class<?> dtoClass) {
            this.entityClass = entityClass;
            this.dtoClass = dtoClass;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            Key other = (Key) o;

            return this.entityClass == other.entityClass && this.dtoClass == other.dtoClass;

        }

        @Override
        public int hashCode() {
            int result = entityClass != null ? entityClass.hashCode() : 0;
            result = 31 * result + (dtoClass != null ? dtoClass.hashCode() : 0);
            return result;
        }
    }

}
