package com.gisdev.library.controller;

import com.gisdev.library.dto.request.book.BookCUDTO;
import com.gisdev.library.dto.response.book.BookDTO;
import com.gisdev.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<Long> createBook(@Valid @RequestBody BookCUDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateBook(@PathVariable Long id, @Valid @RequestBody BookCUDTO request) {

        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteBook(@PathVariable Long id) {

        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @GetMapping("/all")
    public List<BookDTO> getAllBooks( @RequestParam(required = false) List<String> filter, @RequestParam(required = false) String sort) {

        return bookService.getAllBooks(filter, sort);
    }
}