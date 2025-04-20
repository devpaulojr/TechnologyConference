package com.devpaulojr.technologyconference.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PresentationDto(
        UUID id,

        @NotBlank(message = "o campo nome não pode ser vazio")
        String name,
        
        @PastOrPresent(message = "data não pode ser superior o dia de hoje.")
        OffsetDateTime startTime,

        OffsetDateTime endTime,

        @NotNull(message = "O campo room não pode ser vazio.")
        RoomDto room
) {
}
