package com.gisdev.library.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LibraryCreateDTO(

        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Address is required")
        String address
) {}
