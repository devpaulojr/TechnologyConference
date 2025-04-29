package com.devpaulojr.technologyconference.controllers.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDetailsDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String password,
        String confirmPassword,
        String phoneNumber,
        Boolean vip,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
