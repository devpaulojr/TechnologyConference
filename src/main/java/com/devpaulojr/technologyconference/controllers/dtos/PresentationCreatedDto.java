package com.devpaulojr.technologyconference.controllers.dtos;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PresentationCreatedDto(
        UUID id,
        String name,
        OffsetDateTime startTime,
        OffsetDateTime endTime) {
}
