package com.gisdev.library.dto.request.library;

import jakarta.validation.constraints.NotBlank;

public record LibraryCUDTO(

        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Address is required")
        String address
) {}
