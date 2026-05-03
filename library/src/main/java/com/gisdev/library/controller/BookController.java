package com.gisdev.library.controller;

import com.gisdev.library.apiAuth.BookControllerDocs;
import com.gisdev.library.dto.request.book.BaseBookRequestDTO;
import com.gisdev.library.dto.response.book.FullBookResponseDTO;
import com.gisdev.library.service.iservice.IBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final IBookService bookService;

    @PostMapping("/create")
    public ResponseEntity<Long> createBook(@Valid @RequestBody BaseBookRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateBook(@PathVariable Long id, @Valid @RequestBody BaseBookRequestDTO request) {

        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteBook(@PathVariable Long id) {

        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @GetMapping("/all")
    @BookControllerDocs.CreateClientDoc
    //@Operation(description = "Allowed operations for filtering:  \"eq\", \"neq\", \"gt\", \"geq\", \"lt\", \"leq\", \"ilike\"  \n" +
              //               "Allowed sorting fields: \"title\", \"author\", \"genre\", \"section\", \"price\", \"year_of_publication\" ")
    public List<FullBookResponseDTO> getAllBooks(@RequestParam(required = false) List<String> filter, @RequestParam(required = false) String sort) {

        return bookService.getAllBooks(filter, sort);
    }
}