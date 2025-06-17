package com.devpaulojr.technologyconference.security.mapper;

import com.devpaulojr.technologyconference.security.UserAccount;
import com.devpaulojr.technologyconference.security.dto.UserAccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {

    @Mapping(source = "id", target = "id")
    UserAccountDto toDto(UserAccount userAccount);

    @Mapping(source = "id", target = "id")
    UserAccount toEntity(UserAccountDto userAccountDto);
}

