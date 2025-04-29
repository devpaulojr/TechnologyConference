package com.devpaulojr.technologyconference.controllers.mappers.response;

import com.devpaulojr.technologyconference.controllers.dtos.response.UserDetailsDto;
import com.devpaulojr.technologyconference.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDetailsMapper {

    @Mapping(source = "id", target = "id")
    UserDetailsDto toDto(User user);
}
