package com.gisdev.library.dto.response.book;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Section;
import com.gisdev.library.dto.response.library.ShortLibraryDTO;
import com.gisdev.library.dto.response.order.ShortOrderDTO;

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
        List<ShortOrderDTO> orders,
        List<ShortLibraryDTO> libraries
) {}
