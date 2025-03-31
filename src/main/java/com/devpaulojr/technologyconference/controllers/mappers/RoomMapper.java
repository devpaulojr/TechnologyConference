package com.devpaulojr.technologyconference.controllers.mappers;

import com.devpaulojr.technologyconference.controllers.dtos.RoomDto;
import com.devpaulojr.technologyconference.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(source = "company", target = "company")
    RoomDto toDto(Room room);

    @Mapping(source = "company", target = "company")
    Room toEntity(RoomDto roomDto);
}
