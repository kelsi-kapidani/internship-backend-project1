package com.gisdev.library.controller;

import com.gisdev.library.dto.request.librarybook.BaseLibraryBookRequestDTO;
import com.gisdev.library.dto.response.librarybook.LibraryBookResponseDTO;
import com.gisdev.library.service.iservice.ILibraryBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library-book")
@Controller
@RequiredArgsConstructor
public class LibraryBookController {

    private final ILibraryBookService lbService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/add/{id}")
    public ResponseEntity<Long> addBooksToLibrary(@PathVariable Long id, @Valid @RequestBody BaseLibraryBookRequestDTO request) {

        return ResponseEntity.ok(lbService.addListOfBooks(request, id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<LibraryBookResponseDTO> getAllBookStocks() {

        return lbService.getAllBookStocks();
    }
}
