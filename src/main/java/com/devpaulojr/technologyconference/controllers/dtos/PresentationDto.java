package com.devpaulojr.technologyconference.controllers.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PresentationDto(
        UUID id,

        @NotBlank(message = "o campo nome não pode ser vazio")
        String name,

        @PastOrPresent(message = "data não pode ser superior o dia de hoje.")
        OffsetDateTime startTime,

        @FutureOrPresent(message = "data não pode ser inferior o dia de hoje.")
        OffsetDateTime endTime,

        RoomDto room
) {
}
