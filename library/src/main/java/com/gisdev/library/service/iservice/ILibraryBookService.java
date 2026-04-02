package com.gisdev.library.service.iservice;

import com.gisdev.library.dto.request.librarybook.LibraryBookDTO;
import com.gisdev.library.dto.response.librarybook.LibraryBookStockDTO;
import com.gisdev.library.entity.LibraryBook;

import java.util.List;

public interface ILibraryBookService {

    LibraryBook getLibraryBookByIds(Long bookId, Long libraryId);

    Long addListOfBooks(LibraryBookDTO request, Long libraryId);

    List<LibraryBookStockDTO> getAllBookStocks();
}
