package com.gisdev.library.dto.response;

import com.gisdev.library.entity.Book;
import com.gisdev.library.entity.Library;

import java.util.List;

public record BookLibraryStockDTO(

    Book book,
    List<LibraryStock> stockInLibraries
){
    public record LibraryStock(

            Library library,
            Integer stock
    ){}
}

