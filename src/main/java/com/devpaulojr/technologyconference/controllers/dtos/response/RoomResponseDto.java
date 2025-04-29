package com.devpaulojr.technologyconference.controllers.dtos.response;

import com.devpaulojr.technologyconference.model.enums.RoomStatus;
import com.devpaulojr.technologyconference.model.enums.RoomType;

import java.util.UUID;

public record RoomResponseDto(
        UUID id,
        Integer numberRooms,
        Integer seatCapacity,
        Boolean isOccupied,
        RoomType roomType,
        RoomStatus roomStatus,
        CompanyResponseDto company) {
}
