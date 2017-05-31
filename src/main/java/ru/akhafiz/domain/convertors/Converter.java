package ru.akhafiz.domain.convertors;

import ru.akhafiz.domain.model.BaseEntity;

/**
 * <p></p>
 *
 * @author akhafiz
 */
interface Converter<E extends BaseEntity,D> {

    D convertEntityToDto(E entity, D dto);

    E convertDtoToEntity(E entity, D dto);
}
