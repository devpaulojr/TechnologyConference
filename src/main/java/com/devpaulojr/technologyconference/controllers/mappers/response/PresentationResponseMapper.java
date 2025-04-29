package com.devpaulojr.technologyconference.controllers.mappers.response;

import com.devpaulojr.technologyconference.controllers.dtos.response.PresentationResponseDto;
import com.devpaulojr.technologyconference.model.Presentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PresentationResponseMapper {

    @Mapping(source = "id", target = "id")
    PresentationResponseDto toDto(Presentation presentation);

    @Mapping(source = "id", target = "id")
    Presentation toEntity(PresentationResponseDto presentationResponseDto);
}
