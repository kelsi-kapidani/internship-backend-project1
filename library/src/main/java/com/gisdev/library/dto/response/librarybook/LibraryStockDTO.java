package com.gisdev.library.dto.response.librarybook;

public record LibraryStockDTO(

        Long libraryId,
        String libraryName,
        Integer stock
) {}
