package com.gisdev.library.dto.response;

import com.gisdev.library.entity.Book;

import java.util.List;

public record BookWithLibraryStockResponse (

    Book book,
    List<LibraryStock> stockInLibraries
){}

