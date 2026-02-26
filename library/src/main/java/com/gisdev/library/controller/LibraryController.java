package com.gisdev.library.controller;

import com.gisdev.library.dto.request.library.LibraryCUDTO;
import com.gisdev.library.dto.response.library.LibraryDTO;
import com.gisdev.library.service.LibraryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
@Controller
@RequiredArgsConstructor
public class LibraryController {

    public final LibraryService libraryService;

    @GetMapping("/all")
    public List<LibraryDTO> getAllLibraries () {

        return libraryService.getAllLibraries();
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createLibrary(@Valid @RequestBody LibraryCUDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(libraryService.createLibrary(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLibrary(@PathVariable Long id, @Valid @RequestBody LibraryCUDTO request) {

        return ResponseEntity.ok(libraryService.updateLibrary(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteLibrary(@PathVariable Long id) {

        return ResponseEntity.ok(libraryService.deleteLibrary(id));
    }

}
