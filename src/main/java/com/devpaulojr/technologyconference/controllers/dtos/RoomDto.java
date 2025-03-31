package com.devpaulojr.technologyconference.controllers.dtos;

import com.devpaulojr.technologyconference.model.enums.RoomStatus;
import com.devpaulojr.technologyconference.model.enums.RoomType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record RoomDto(
               UUID id,
               Integer numberRooms,
               Integer seatCapacity,
               Boolean isOccupied,

               @NotNull(message = "O campo roomType não pode ser vazio.")
               RoomType roomType,

               RoomStatus roomStatus,
               LocalDateTime createdAt,
               LocalDateTime updatedAt,

               @NotNull(message = "O campo company não pode ser vazio.")
               CompanyDto company) {
}
