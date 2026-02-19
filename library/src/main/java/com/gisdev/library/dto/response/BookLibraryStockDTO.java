package com.gisdev.library.dto.response;

import java.util.List;

public record BookLibraryStockDTO(

    LibraryDTO.LibraryBooksDTO book,
    List<LibraryStock> stockInLibraries
){
    public record LibraryStock(

            Long libraryId,
            String libraryName,
            Integer stock
    ){}
}

