package com.gisdev.library.dto.response.user;

import com.gisdev.library.constants.enums.Role;

public record ShortUserDTO(

        Long id,
        String name,
        String surname,
        String username,
        Role role,
        String email
) {}
