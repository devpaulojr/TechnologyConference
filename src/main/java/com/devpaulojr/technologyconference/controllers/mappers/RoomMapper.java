package com.devpaulojr.technologyconference.controllers.mappers;

import com.devpaulojr.technologyconference.controllers.dtos.RoomDto;
import com.devpaulojr.technologyconference.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(source = "company", target = "companyDto")
    RoomDto toDto(Room room);

    @Mapping(source = "companyDto", target = "company")
    Room toEntity(RoomDto roomDto);
}
