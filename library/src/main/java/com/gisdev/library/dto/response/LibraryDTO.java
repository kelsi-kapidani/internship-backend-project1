package com.gisdev.library.dto.response;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Role;
import com.gisdev.library.constants.enums.Section;

import java.time.LocalDate;
import java.util.List;

public record LibraryDTO(

        Long id,
        String name,
        String address,
        List<LibraryUsersDTO> users,
        List<LibraryBooksDTO> books
) {

    public record LibraryUsersDTO(

            Long id,
            String name,
            String surname,
            String username,
            Role role,
            String email
    ) {}

    public record LibraryBooksDTO(

            Long id,
            String title,
            String author,
            Genre genre,
            Section section,
            String price,
            LocalDate yearOfPublication
    ) {}
}
