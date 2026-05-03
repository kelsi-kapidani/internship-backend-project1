package com.gisdev.library.dto.request.book;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Section;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseBookRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotNull(message = "Genre is required")
    private Genre genre;

    @NotNull(message = "Section is required")
    private Section section;

    @NotBlank(message = "Price is required")
    @Pattern(
            regexp = "^(?!0+(\\.0+)?$)\\d+(\\.\\d+)?$",
            message = "Price must be a number greater than zero"
    )
    private String price;

    @NotNull(message = "Year of publication is required")
    private LocalDate year_of_publication;

}
