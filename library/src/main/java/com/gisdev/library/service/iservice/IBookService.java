package com.gisdev.library.service.iservice;

import com.gisdev.library.dto.request.book.BookCUDTO;
import com.gisdev.library.dto.response.book.BookDTO;
import com.gisdev.library.dto.response.book.FullBookDTO;
import com.gisdev.library.entity.Book;

import java.util.List;
import java.util.Optional;

public interface IBookService {

    boolean existsByTitle(String title);

    boolean existsById(Long id);

    Optional<Book> getBookById(Long id);

    List<Book> getAllWithLibraryBooks();

    Long createBook(BookCUDTO request);

    Long updateBook(Long id, BookCUDTO request);

    Long deleteBook(Long id);

    List<FullBookDTO> getAllBooks(List<String> filter, String sort);
}
