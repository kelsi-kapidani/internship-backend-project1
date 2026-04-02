package com.gisdev.library.dto.response.book;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Section;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseBookDTO {

    private Long id;
    private String title;
    private String author;
    private Genre genre;
    private Section section;
    private String price;
    private LocalDate year_of_publication;
}