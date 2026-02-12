package com.gisdev.library.dto.request;

import jakarta.validation.constraints.*;

public record CreateUserRequest (

    @NotBlank(message = "Name is required")
    String name,

    @NotBlank(message = "Surname is required")
    String surname,

    @NotBlank(message = "Username is required")
    String username,

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = ".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*",
            message = "Password must contain at least one special character"
    )
    String password,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    String email,

    @NotNull(message = "Library id is required")
    Long libraryId
)   {}
