package com.gisdev.library.dto.request.librarybook;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseLibraryBookRequestDTO {

    @NotNull(message = "List of books is empty")
    private List<LibraryBookAmountRequestDTO> books;
}
