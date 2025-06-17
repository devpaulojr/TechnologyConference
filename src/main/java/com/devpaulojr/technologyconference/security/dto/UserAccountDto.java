package com.devpaulojr.technologyconference.security.dto;

import java.util.List;
import java.util.UUID;

public record UserAccountDto(
        UUID id,
        String login,
        String password,
        List<String> roles) {
}
