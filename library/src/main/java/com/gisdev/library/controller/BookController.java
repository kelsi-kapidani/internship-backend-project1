package com.gisdev.library.controller;

import com.gisdev.library.dto.request.BookCreateDTO;
import com.gisdev.library.dto.request.BookUpdateDTO;
import com.gisdev.library.dto.response.BookDTO;
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
    public Object createBook(@Valid @RequestBody BookCreateDTO request) {

        return bookService.createBook(request);
    }

    @PutMapping("/{id}")
    public Object updateBook(@PathVariable Long id, @Valid @RequestBody BookUpdateDTO request) {

        return bookService.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    public Object deleteBook(@PathVariable Long id) {

        return bookService.deleteBook(id);
    }

    @GetMapping("/all")
    public List<BookDTO> getAllBooks() {

        return bookService.getAllBooks();
    }
}