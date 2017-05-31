package ru.akhafiz.domain.convertors;

import ru.akhafiz.domain.dto.UserDto;
import ru.akhafiz.domain.model.User;

/**
 * <p></p>
 *
 * @author akhafiz
 */
class SimpleUserConverter implements Converter<User,UserDto> {

    @Override
    public UserDto convertEntityToDto(User entity, UserDto dto) {
        return dto;
    }

    @Override
    public User convertDtoToEntity(User entity, UserDto dto) {
        return entity;
    }
}
