package com.devpaulojr.technologyconference.controllers.mappers;

import com.devpaulojr.technologyconference.controllers.dtos.PresentationCreatedDto;
import com.devpaulojr.technologyconference.model.Presentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PresentationCreatedMapper {

    @Mapping(source = "id", target = "id")
    PresentationCreatedDto toDto(Presentation presentation);
}
