package com.devpaulojr.technologyconference.controllers.dtos;

import com.devpaulojr.technologyconference.controllers.dtos.response.UserResponseDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CompanyDto(
        UUID id,

        @NotBlank(message = "Nome não pode ser vazio.")
        @Size(message = "Nome inválido, tente outra nome", min = 1, max = 80)
        String name,

        @NotBlank(message = "CNPJ não pode ser vazio.")
        @CNPJ(message = "CNPJ inválido.")
        String cnpj,

        @NotBlank(message = "Endereço não pode ser vazio.")
        @Size(message = "Endereço inválido, tente outro endereço", min = 1, max = 80)
        String address,

        @NotBlank(message = "Rua não pode ser vazio.")
        @Size(message = "Bairro inválido, tente outra bairro", min = 1, max = 80)
        String neighborhood,

        @NotBlank(message = "Cidade não pode ser vazio.")
        @Size(message = "Cidade inválido, tente outra cidade", min = 1, max = 80)
        String city,

        @NotBlank(message = "Estado não pode ser vazio.")
        @Size(message = "Estado inválido, digite apenas dois caracteres.", min = 2, max = 2)
        String state,

        @PastOrPresent(message = "data não pode ser superior o dia de hoje.")
        LocalDateTime createdAt,

        @FutureOrPresent(message = "data não pode ser inferior ao dia de hoje.")
        LocalDateTime updatedAt,

        List<UserResponseDto> users) {
}
