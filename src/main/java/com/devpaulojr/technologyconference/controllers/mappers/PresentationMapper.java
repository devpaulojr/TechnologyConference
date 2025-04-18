package com.devpaulojr.technologyconference.controllers.mappers;

import com.devpaulojr.technologyconference.controllers.dtos.PresentationDto;
import com.devpaulojr.technologyconference.model.Presentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PresentationMapper {

    @Mapping(source = "id", target = "id")
    PresentationDto toDto(Presentation presentation);

    @Mapping(source = "room", target = "room")
    Presentation toEntity(PresentationDto presentationDto);
}
