package com.gisdev.library.service.iservice;

import com.gisdev.library.dto.request.book.BookCUDTO;
import com.gisdev.library.dto.response.book.BookDTO;

import java.util.List;

public interface IBookService {

    Long createBook(BookCUDTO request);

    Long updateBook(Long id, BookCUDTO request);

    Long deleteBook(Long id);

    List<BookDTO> getAllBooks(List<String> filter, String sort);
}
