package com.gisdev.library.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LibraryUpdateDTO(

        @NotBlank String name,
        @NotBlank String address
) {}
