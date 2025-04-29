package com.devpaulojr.technologyconference.controllers.mappers.response;

import com.devpaulojr.technologyconference.controllers.dtos.response.CompanyResponseDto;
import com.devpaulojr.technologyconference.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyResponseMapper {

    @Mapping(source = "id", target = "id")
    CompanyResponseDto toDto(Company company);

    @Mapping(source = "id", target = "id")
    CompanyResponseDto toEntity(Company company);
}
