package com.gisdev.library.dto.request;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Section;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookUpdateDTO(

        @NotBlank String title,
        @NotBlank String author,
        @NotNull Genre genre,
        @NotNull Section section,
        @NotBlank String price,
        @NotNull LocalDate yearOfPublication
) {}