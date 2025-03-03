package com.devpaulojr.technologyconference.controllers.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserCreatedDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Boolean vip,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
