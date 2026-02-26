package com.gisdev.library.dto.response.librarybook;

import com.gisdev.library.dto.response.book.ShortBookDTO;

import java.util.List;

public record LibraryBookStockDTO(

    ShortBookDTO book,
    List<LibraryStockDTO> stockInLibraries
){}

