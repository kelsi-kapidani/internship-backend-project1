package com.gisdev.library.service;

import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.request.BookCreateDTO;
import com.gisdev.library.dto.request.BookUpdateDTO;
import com.gisdev.library.entity.Book;
import com.gisdev.library.exception.BadRequestException;
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

    public Object createBook(BookCreateDTO request) {

        if (titleExists(request.title())) {
            return new BadRequestException("Book with this title already exists");
        }
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

    public Object updateBook(Long id, BookUpdateDTO request) {

        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return new BadRequestException("Book with this id does not exist");
        }
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setGenre(request.genre());
        book.setSection(request.section());
        book.setPrice(request.price());
        book.setYearOfPublication(request.yearOfPublication());

        return bookRepository.save(book);
    }

    public Object deleteBook(Long id) {

        if (!idExists(id)) {
            return new BadRequestException("Book with this id does not exist");
        }
        bookRepository.deleteById(id);
        return new ResponseError("Deletion successful");
    }

    public List<Book> getAllBooks() {

        return bookRepository.findAll();
    }

    public List<Book> getAllbooks() {

        return bookRepository.findAll();
    }
}