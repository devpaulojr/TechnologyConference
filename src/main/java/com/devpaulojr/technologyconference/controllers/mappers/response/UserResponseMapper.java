package com.devpaulojr.technologyconference.controllers.mappers.response;

import com.devpaulojr.technologyconference.controllers.dtos.response.UserResponseDto;
import com.devpaulojr.technologyconference.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    @Mapping(source = "id", target = "id")
    UserResponseDto toDto(User user);
}
