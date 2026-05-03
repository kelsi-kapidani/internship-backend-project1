package com.gisdev.library.service.iservice;

import com.gisdev.library.dto.request.book.BaseBookRequestDTO;
import com.gisdev.library.dto.response.book.FullBookResponseDTO;
import com.gisdev.library.entity.Book;

import java.util.List;
import java.util.Optional;

public interface IBookService {

    void existsByTitle(String title);

    void existsById(Long id);

    Optional<Book> getBookById(Long id);

    List<Book> getAllWithLibraryBooks();

    Long createBook(BaseBookRequestDTO request);

    Long updateBook(Long id, BaseBookRequestDTO request);

    Long deleteBook(Long id);

    List<FullBookResponseDTO> getAllBooks(List<String> filter, String sort);
}
