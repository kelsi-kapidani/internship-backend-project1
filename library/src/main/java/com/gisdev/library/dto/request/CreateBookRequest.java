package com.gisdev.library.dto.request;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Section;

import java.time.LocalDate;

public record CreateBookRequest(
        String title,
        String author,
        Genre genre,
        Section section,
        LocalDate yearOfPublication
) {}