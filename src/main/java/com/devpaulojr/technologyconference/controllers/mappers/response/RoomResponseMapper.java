package com.devpaulojr.technologyconference.controllers.mappers.response;

import com.devpaulojr.technologyconference.controllers.dtos.response.RoomResponseDto;
import com.devpaulojr.technologyconference.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomResponseMapper {

    @Mapping(source = "id", target = "id")
    RoomResponseDto toDto(Room room);
}
