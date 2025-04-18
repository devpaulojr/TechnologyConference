package com.devpaulojr.technologyconference.controllers.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record PresentationDto(
        UUID id,
        String name,
        LocalDateTime startTime,
        LocalDateTime endTime,
        RoomDto room
) {
}
