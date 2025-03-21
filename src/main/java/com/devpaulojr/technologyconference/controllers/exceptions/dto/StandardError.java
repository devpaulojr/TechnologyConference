package com.devpaulojr.technologyconference.controllers.exceptions.dto;

import java.time.Instant;
import java.util.List;

public record StandardError(
        Instant timestamp,
        int status,
        String path,
        String message,
        List<String> detailsMessage
        ) {
}
