package com.gisdev.library.controller;

import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.request.LibraryCreateDTO;
import com.gisdev.library.dto.request.LibraryUpdateDTO;
import com.gisdev.library.entity.Library;
import com.gisdev.library.service.LibraryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public List<Library> getAllLibraries () {

        return libraryService.getAllLibraries();
    }

    @PostMapping("/create")
    public Object createLibrary(@Valid @RequestBody LibraryCreateDTO request) {

        return libraryService.createLibrary(request);
    }

    @PutMapping("/{id}")
    public Object updateLibrary(@PathVariable Long id, @Valid @RequestBody LibraryUpdateDTO request) {

        return libraryService.updateLibrary(id, request);
    }

    @DeleteMapping("/{id}")
    public Object deleteLibrary(@PathVariable Long id) {

        return libraryService.deleteLibrary(id);
    }

}
