package com.gisdev.library.controller;

import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.request.CreateLibraryRequest;
import com.gisdev.library.dto.request.UpdateLibraryRequest;
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

    @GetMapping("/{id}")
    public Object getLibrary(@PathVariable long id) {

        if (!libraryService.idExists(id)) {
            return new ResponseError("Library with this id does not exist");
        }
        return libraryService.getLibrary(id);
    }

    @PostMapping("/create")
    public Object createLibrary(@Valid @RequestBody CreateLibraryRequest request) {

        if (libraryService.nameExists(request.name())) {
            return new ResponseError("Library with this name already exists");
        }
        return libraryService.createLibrary(request);
    }

    @PutMapping("/{id}")
    public Object updateLibrary(@PathVariable Long id, @RequestBody UpdateLibraryRequest request) {
        if (!libraryService.idExists(id)) {
            return new ResponseError("Library with this id does not exist");
        }
        return libraryService.updateLibrary(id, request);
    }

    @DeleteMapping("/{id}")
    public Object deleteLibrary(@PathVariable Long id) {
        if (!libraryService.idExists(id)) {
            return new ResponseError("Library with this id does not exist");
        }
        libraryService.deleteLibrary(id);
        return new ResponseError("Deletion successfull");
    }

}
