package com.gisdev.library.service;

import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.request.BookCreateDTO;
import com.gisdev.library.dto.request.BookUpdateDTO;
import com.gisdev.library.dto.response.BookDTO;
import com.gisdev.library.entity.Book;
import com.gisdev.library.entity.BookOrder;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.mapper.BookMapper;
import com.gisdev.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public boolean titleExists(String title) {
        return bookRepository.existsByTitle(title);
    }

    public boolean idExists(Long id) {
        return bookRepository.existsById(id);
    }

    public Object createBook(BookCreateDTO request) {

        if (titleExists(request.title())) {
            return new BadRequestException("Book with this title already exists");
        }
        Book book = bookMapper.toEntity(request);
        bookRepository.save(book);
        return new ResponseError("Book created sucessfully");
    }

    public Object updateBook(Long id, BookUpdateDTO request) {

        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return new BadRequestException("Book with this id does not exist");
        }

        bookMapper.updateBookFromDto(request, book);
        bookRepository.save(book);
        return new ResponseError("Book updated successfully");
    }

    public Object deleteBook(Long id) {

        if (!idExists(id)) {
            return new BadRequestException("Book with this id does not exist");
        }
        bookRepository.deleteById(id);
        return new ResponseError("Deletion successful");
    }

    public List<BookDTO> getAllBooks() {

        List<BookDTO> response = new ArrayList<>();
        for (Book book: bookRepository.findAll()) {
            response.add(bookMapper.toDto(book));
        }
        return response;
    }

}