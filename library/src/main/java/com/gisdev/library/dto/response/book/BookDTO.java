package com.gisdev.library.dto.response.book;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Section;
import com.gisdev.library.dto.response.library.ShortLibraryDTO;
import com.gisdev.library.dto.response.order.ShortOrderDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record BookDTO(

        @Schema(example = "1")
        Long id,
        @Schema(example = "Shkelqimi dhe Renia e Shokut Zylo")
        String title,
        @Schema(example = "Ismail Kadare")
        String author,
        @Schema(example = "HISTORY")
        Genre genre,
        @Schema(example = "ALBANIAN_BOOK")
        Section section,
        @Schema(example = "1000")
        String price,
        @Schema(example = "2026-03-03")
        LocalDate yearOfPublication,
        List<ShortOrderDTO> orders,
        List<ShortLibraryDTO> libraries
) {}
