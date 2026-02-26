package com.gisdev.library.dto.response.book;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Section;

import java.time.LocalDate;

public record ShortBookDTO(

        Long id,
        String title,
        String author,
        Genre genre,
        Section section,
        String price,
        LocalDate yearOfPublication
) {}
