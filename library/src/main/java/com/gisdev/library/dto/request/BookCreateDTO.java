package com.gisdev.library.dto.request;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Section;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record BookCreateDTO(

        @NotBlank(message = "Title is required")
        String title,
        @NotBlank(message = "Author is required")
        String author,
        @NotNull(message = "Genre is required")
        Genre genre,
        @NotNull(message = "Section is required")
        Section section,
        @NotBlank(message = "Price is required")
        @Pattern(
                regexp = "^(?!0+(\\.0+)?$)\\d+(\\.\\d+)?$",
                message = "Price must be a number greater than zero"
        )
        String price,
        @NotNull(message = "Year of publication is required")
        LocalDate yearOfPublication
) {}