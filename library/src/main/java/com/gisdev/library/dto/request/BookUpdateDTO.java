package com.gisdev.library.dto.request;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Section;

import java.time.LocalDate;

public record BookUpdateDTO(

        String title,
        String author,
        Genre genre,
        Section section,
        String price,
        LocalDate yearOfPublication
) {}