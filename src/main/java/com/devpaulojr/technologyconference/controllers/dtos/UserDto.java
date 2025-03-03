package com.devpaulojr.technologyconference.controllers.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDto(

        UUID id,

        @NotBlank(message = "valores não pode ser vazios")
        @Size(message = "tamanho fora do padrão.", min = 1, max = 80)
        String firstName,

        @NotBlank(message = "valores não pode ser vazios")
        @Size(message = "tamanho fora do padrão.", min = 1, max = 80)
        String lastName,

        @NotBlank(message = "valores não pode ser vazios")
        @Size(message = "tamanho fora do padrão.", min = 1, max = 100)
        @Email(message = "email inválido.")
        String email,

        @NotBlank(message = "valores não pode ser vazios")
        @Size(message = "tamanho fora do padrão.", min = 1, max = 100)
        String password,

        @NotBlank(message = "valores não pode ser vazios")
        @Size(message = "tamanho fora do padrão.", min = 1, max = 100)
        String confirmPassword,

        @NotBlank(message = "valores não pode ser vazios")
        @Size(message = "celular inválido.", min = 11, max = 11)
        String phoneNumber,

        @NotNull(message = "valores inválido.")
        Boolean vip,

        @PastOrPresent(message = "data não pode ser superior o dia de hoje.")
        LocalDateTime createdAt,

        @FutureOrPresent(message = "data não pode ser inferior ao dia de hoje.")
        LocalDateTime updatedAt
) {
}
