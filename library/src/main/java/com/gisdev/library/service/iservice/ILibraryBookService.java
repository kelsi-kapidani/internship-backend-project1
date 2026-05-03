package com.gisdev.library.service.iservice;

import com.gisdev.library.dto.request.librarybook.BaseLibraryBookRequestDTO;
import com.gisdev.library.dto.response.librarybook.LibraryBookResponseDTO;
import com.gisdev.library.entity.LibraryBook;

import java.util.List;

public interface ILibraryBookService {

    LibraryBook getLibraryBookByIds(Long bookId, Long libraryId);

    Long addListOfBooks(BaseLibraryBookRequestDTO request, Long libraryId);

    List<LibraryBookResponseDTO> getAllBookStocks();
}
