package com.gisdev.library.controller;

import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.request.CreateBookRequest;
import com.gisdev.library.dto.request.UpdateBookRequest;
import com.gisdev.library.entity.Book;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public Object createBook(@Valid @RequestBody CreateBookRequest request) {

        if (bookService.titleExists(request.title())) {
            return new ResponseError("Book with this title already exists");
        }

        return bookService.createBook(request);
    }

    @PutMapping("/{id}")
    public Object updateBook(@PathVariable Long id, @RequestBody UpdateBookRequest request) {

        if (!bookService.idExists(id)) {
            return new ResponseError("Book with this id does not exist");
        }

        return bookService.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    public Object deleteBook(@PathVariable Long id) {

        if (!bookService.idExists(id)) {
                return new BadRequestException("Book with this id does not exist");
        }

        bookService.deleteBook(id);
        return new ResponseError("Deletion successful");
    }

    @GetMapping("/all")
    public List<Book> getAllBooks() {

        return bookService.getAllBooks();
    }
}