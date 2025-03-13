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

        @NotBlank(message = "primeiro nome não pode ser vazios")
        @Size(message = "Primeiro nome inválido, tente outra nome", min = 1, max = 80)
        String firstName,

        @NotBlank(message = "Sobrenome não pode ser vazios")
        @Size(message = "Sobrenome inválido, tente outra nome", min = 1, max = 80)
        String lastName,

        @NotBlank(message = "E-mail não pode ser vazios")
        @Size(message = "E-mail inválida, tente outra email", min = 1, max = 100)
        @Email(message = "E-mail inválido.")
        String email,

        @NotBlank(message = "Usuário ou senha incorretos.")
        @Size(message = "Senha inválida, tente outra senha", min = 1, max = 100)
        String password,

        @NotBlank(message = "Usuário ou senha incorretos.")
        @Size(message = "Senha inválida, tente outra senha", min = 1, max = 100)
        String confirmPassword,

        @NotBlank(message = "Celular não pode ser vazio")
        @Size(message = "Celular inválido, tente outra telefone", min = 11, max = 11)
        String phoneNumber,

        @NotNull(message = "Vip inválido, preencher o valor")
        Boolean vip,

        @PastOrPresent(message = "data não pode ser superior o dia de hoje.")
        LocalDateTime createdAt,

        @FutureOrPresent(message = "data não pode ser inferior ao dia de hoje.")
        LocalDateTime updatedAt
) {
}
