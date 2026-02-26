package com.gisdev.library.service.iservice;

import com.gisdev.library.dto.request.librarybook.LibraryBookAddDTO;
import com.gisdev.library.dto.response.librarybook.LibraryBookStockDTO;

import java.util.List;

public interface ILibraryBookService {
    Long addListOfBooks(LibraryBookAddDTO request, Long libraryId);

    List<LibraryBookStockDTO> getAllBookStocks();
}
