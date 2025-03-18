package com.devpaulojr.technologyconference.controllers.mappers;

import com.devpaulojr.technologyconference.controllers.dtos.CompanyDto;
import com.devpaulojr.technologyconference.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(source = "users", target = "users")
    CompanyDto toDto(Company company);

    @Mapping(source = "users", target = "users")
    Company toEntity(CompanyDto dto);

}
