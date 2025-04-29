package com.devpaulojr.technologyconference.controllers.dtos.response;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Boolean vip) {
}
