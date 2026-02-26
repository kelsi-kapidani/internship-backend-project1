package com.gisdev.library.controller;

import com.gisdev.library.dto.request.librarybook.LibraryBookAddDTO;
import com.gisdev.library.dto.response.librarybook.LibraryBookStockDTO;
import com.gisdev.library.service.LibraryBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library-book")
@Controller
@RequiredArgsConstructor
public class LibraryBookController {

    public final LibraryBookService lbService;

    @PutMapping("/add/{id}")
    public ResponseEntity<Long> addBooksToLibrary(@PathVariable Long id, @Valid @RequestBody LibraryBookAddDTO request) {

        return ResponseEntity.ok(lbService.addListOfBooks(request, id));
    }

    @GetMapping
    public List<LibraryBookStockDTO> getAllBookStocks() {

        return lbService.getAllBookStocks();
    }
}
