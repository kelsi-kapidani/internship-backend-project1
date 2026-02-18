package com.gisdev.library.service;

import com.gisdev.library.dto.request.BookCreateDTO;
import com.gisdev.library.dto.request.BookUpdateDTO;
import com.gisdev.library.entity.Book;
import com.gisdev.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public boolean titleExists(String title) {
        return bookRepository.existsByTitle(title);
    }

    public boolean idExists(Long id) {
        return bookRepository.existsById(id);
    }

    public Book createBook(BookCreateDTO request) {

        Book book = Book.builder()
                .title(request.title())
                .author(request.author())
                .genre(request.genre())
                .section(request.section())
                .price(request.price())
                .yearOfPublication(request.yearOfPublication())
                .build();

        return bookRepository.save(book);
    }

    public Book updateBook(Long id, BookUpdateDTO request) {

        Book book = bookRepository.findById(id).orElse(null);

        if (book == null) {
            return null;
        }

        if (request.title() != null) {
            book.setTitle(request.title());
        }
        if (request.author() != null) {
            book.setAuthor(request.author());
        }
        if (request.genre() != null) {
            book.setGenre(request.genre());
        }
        if (request.section() != null) {
            book.setSection(request.section());
        }
        if (request.price() != null) {
            book.setPrice(request.price());
        }
        if (request.yearOfPublication() != null) {
            book.setYearOfPublication(request.yearOfPublication());
        }

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {

        bookRepository.deleteById(id);
    }

    public List<Book> getAllBooks() {

        return bookRepository.findAll();
    }

    public Book getBook(Long id) {

        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> getAllbooks() {

        return bookRepository.findAll();
    }
}