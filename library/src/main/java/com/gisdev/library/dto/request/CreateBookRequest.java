package com.gisdev.library.dto.request;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Section;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

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
        @Pattern(
                regexp = "^(?!0+(\\.0+)?$)\\d+(\\.\\d+)?$",
                message = "Price must be a number greater than zero"
        )
        String price,
        @NotBlank(message = "Year of publication is required")
        LocalDate yearOfPublication
) {}