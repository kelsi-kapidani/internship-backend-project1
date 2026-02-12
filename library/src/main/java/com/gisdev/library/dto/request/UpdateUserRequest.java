package com.gisdev.library.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(

    String name,
    String surname,

    String username,
    String password,

    String email,

    Boolean active,
    Long libraryId
) {}
