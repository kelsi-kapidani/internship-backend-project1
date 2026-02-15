package com.gisdev.library.dto.request;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Section;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateBookRequest(

        @NotBlank(message = "Title is required")
        String title,
        @NotBlank(message = "Author is required")
        String author,
        @NotBlank(message = "Genre is required")
        Genre genre,
        @NotBlank(message = "Section is required")
        Section section,
        @NotBlank(message = "Price is required")
        String price,
        @NotBlank(message = "Year of publication is required")
        LocalDate yearOfPublication
) {}