package com.gisdev.library.dto.response;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Section;
import com.gisdev.library.constants.enums.Status;

import java.time.LocalDate;
import java.util.List;

public record BookDTO(

        Long id,
        String title,
        String author,
        Genre genre,
        Section section,
        String price,
        LocalDate yearOfPublication,
        List<BookOrdersDTO> orders,
        List<BookLibrariesDTO> libraries
) {
    public record BookLibrariesDTO(

            Long id,
            String name,
            String address
    ) {}

    public record BookOrdersDTO(

            Long id,
            Status status
    ) {}
}
