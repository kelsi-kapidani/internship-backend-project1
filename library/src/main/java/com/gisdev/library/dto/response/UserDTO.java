package com.gisdev.library.dto.response;

import com.gisdev.library.constants.enums.Role;

public record UserDTO(

        Long id,
        String name,
        String surname,
        String username,
        String password,
        String email,
        boolean active,
        Role role,
        UserLibraryDTO library
) {

    public record UserLibraryDTO(

            Long id,
            String name,
            String address
    ) {}
}
