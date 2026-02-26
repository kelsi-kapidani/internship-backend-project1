package com.gisdev.library.dto.request.librarybook;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record LibraryBookAddDTO(

        @NotNull(message = "List of books is empty")
        List<BookAddDTO> books
) {

        public record BookAddDTO(

                Long id,
                Integer amount
        ) {}
}
