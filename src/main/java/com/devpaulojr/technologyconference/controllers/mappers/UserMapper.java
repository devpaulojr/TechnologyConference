package com.devpaulojr.technologyconference.controllers.mappers;

import com.devpaulojr.technologyconference.controllers.dtos.UserDto;
import com.devpaulojr.technologyconference.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    UserDto toDto(User user);

    @Mapping(source = "id", target = "id")
    User toEntity(UserDto userDto);
}
