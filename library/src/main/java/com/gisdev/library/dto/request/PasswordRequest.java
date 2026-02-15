package com.gisdev.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PasswordRequest(

        @NotBlank(message = "Password is empty")
        @Pattern(
                regexp = ".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*",
                message = "Password must contain at least one special character"
        )
        String password
) {}
