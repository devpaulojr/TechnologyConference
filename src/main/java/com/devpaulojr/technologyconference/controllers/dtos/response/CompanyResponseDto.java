package com.devpaulojr.technologyconference.controllers.dtos.response;

import java.util.List;
import java.util.UUID;

public record CompanyResponseDto(
        UUID id,
        String name,
        String cnpj,
        String address,
        String neighborhood,
        String city,
        String state,
        List<UserResponseDto> users) {
}
