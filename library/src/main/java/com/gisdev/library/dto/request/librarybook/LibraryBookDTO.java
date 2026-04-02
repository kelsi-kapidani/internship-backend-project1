package com.gisdev.library.dto.request.librarybook;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record LibraryBookDTO(

        @NotNull(message = "List of books is empty")
        List<BookAddDTO> books
) {

        public record BookAddDTO(

                @NotNull(message = "Id of book in the list not given")
                Long id,
                @NotNull(message = "No given amount for book in the list")
                @Positive(message = "Not appropriate amount number given for book in the list")
                Integer amount
        ) {}
}
