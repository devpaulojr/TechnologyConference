package com.devpaulojr.technologyconference.controllers.dtos;

import com.devpaulojr.technologyconference.model.enums.RoomStatus;
import com.devpaulojr.technologyconference.model.enums.RoomType;

import java.util.UUID;

public record RoomDto(
               UUID id,
               Integer numberRooms,
               Integer seatCapacity,
               Boolean isOccupied,
               RoomType roomType,
               RoomStatus roomStatus,
               CompanyDto companyDto) {
}
