package com.gisdev.library.controller;

import com.gisdev.library.dto.request.LibraryBookAddDTO;
import com.gisdev.library.dto.response.BookLibraryStockDTO;
import com.gisdev.library.service.LibraryBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public Object addBooksToLibrary(@PathVariable Long id, @Valid @RequestBody LibraryBookAddDTO request) {

        return lbService.addListOfBooks(request.idOfBooks(), id);
    }

    @GetMapping
    public List<BookLibraryStockDTO> getAllBookStocks() {

        return lbService.getAllBookStocks();
    }
}
