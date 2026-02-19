package com.gisdev.library.service;

import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.request.LibraryCreateDTO;
import com.gisdev.library.dto.request.LibraryUpdateDTO;
import com.gisdev.library.entity.Library;
import com.gisdev.library.exception.BadRequestException;
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

    public Object createLibrary(LibraryCreateDTO request) {

        if (nameExists(request.name())) {
            return new BadRequestException("Library with this name already exists");
        }
        Library library = Library.builder()
                .name(request.name())
                .address(request.address())
                .build();

        return libraryRepository.save(library);
    }

    public Object updateLibrary(Long id, LibraryUpdateDTO request) {

        Library library = libraryRepository.findById(id).orElse(null);

        if (library == null) {
            return new BadRequestException("Library with this id does not exist");
        }
        library.setName(request.name());
        library.setAddress(request.address());

        return libraryRepository.save(library);
    }

    public Object deleteLibrary(Long id) {

        if (idExists(id)) {
            return new ResponseError("Library with this id does not exist");
        }
        libraryRepository.deleteById(id);
        return new ResponseError("Deletion successfull");
    }

    public List<Library> getAllLibraries() {

        return libraryRepository.findAll();
    }

    public Library getLibrary(Long id) {

        return libraryRepository.findById(id).orElse(null);
    }

}
