package com.devpaulojr.technologyconference.controllers.dtos.response;

import jakarta.validation.constraints.PastOrPresent;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PresentationResponseDto(
        UUID id,
        String name,

        @PastOrPresent(message = "data não pode ser superior o dia de hoje.")
        OffsetDateTime startTime,

        OffsetDateTime endTime,
        RoomResponseDto room) {
}
