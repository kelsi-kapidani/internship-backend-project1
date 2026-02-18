package com.gisdev.library.service;

import com.gisdev.library.dto.request.LibraryCreateDTO;
import com.gisdev.library.dto.request.LibraryUpdateDTO;
import com.gisdev.library.entity.Library;
import com.gisdev.library.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {

    public final LibraryRepository libraryRepository;

    public boolean nameExists(String username) {
        return libraryRepository.existsByName(username);
    }

    public boolean idExists(Long id) {
        return libraryRepository.existsById(id);
    }

    public Library createLibrary(LibraryCreateDTO request) {

        Library library = Library.builder()
                .name(request.name())
                .address(request.address())
                .build();

        return libraryRepository.save(library);
    }

    public Library updateLibrary(Long id, LibraryUpdateDTO request) {

        Library library = libraryRepository.findById(id).orElse(null);

        if (library == null) {
            return library;
        }

        if (request.name() != null) {
           library.setName(request.name());
        }
        if (request.address() != null) {
            library.setAddress(request.address());
        }

        return libraryRepository.save(library);
    }

    public void deleteLibrary(Long id) {

        libraryRepository.deleteById(id);
    }

    public List<Library> getAllLibraries() {

        return libraryRepository.findAll();
    }

    public Library getLibrary(Long id) {

        return libraryRepository.findById(id).orElse(null);
    }

}
