package com.gisdev.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateDTO(

    @NotBlank String name,
    @NotBlank String surname,
    @NotBlank String username,
    @NotBlank String password,
    @NotBlank String email,
    @NotNull Long libraryId
) {}
