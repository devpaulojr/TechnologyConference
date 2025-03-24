package com.devpaulojr.technologyconference.controllers.mappers;

import com.devpaulojr.technologyconference.controllers.dtos.UserDto;
import com.devpaulojr.technologyconference.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "company", target = "company")
    UserDto toDto(User user);

    @Mapping(source = "company", target = "company")
    User toEntity(UserDto userDto);
}
