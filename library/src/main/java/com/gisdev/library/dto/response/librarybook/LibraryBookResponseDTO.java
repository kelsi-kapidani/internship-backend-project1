package com.gisdev.library.dto.response.librarybook;

import com.gisdev.library.dto.response.book.BaseBookResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryBookResponseDTO {

    private BaseBookResponseDTO book;
    private List<LibraryBookStockResponseDTO> stockInLibraries;
}
