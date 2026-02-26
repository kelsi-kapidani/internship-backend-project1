package com.gisdev.library.dto.response.user;

import com.gisdev.library.constants.enums.Role;
import com.gisdev.library.dto.response.library.ShortLibraryDTO;

public record UserDTO(

        Long id,
        String name,
        String surname,
        String username,
        String email,
        boolean active,
        Role role,
        ShortLibraryDTO library
) {}
