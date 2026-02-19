package com.gisdev.library.service;

import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.request.LibraryCreateDTO;
import com.gisdev.library.dto.request.LibraryUpdateDTO;
import com.gisdev.library.dto.response.BookDTO;
import com.gisdev.library.dto.response.LibraryDTO;
import com.gisdev.library.entity.Book;
import com.gisdev.library.entity.Library;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.mapper.LibraryMapper;
import com.gisdev.library.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {

    public final LibraryRepository libraryRepository;
    public final LibraryMapper libraryMapper;

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
        Library library = libraryMapper.toEntity(request);
        libraryRepository.save(library);
        return new ResponseError("Library created successfully");
    }

    public Object updateLibrary(Long id, LibraryUpdateDTO request) {

        Library library = libraryRepository.findById(id).orElse(null);
        if (library == null) {
            return new BadRequestException("Library with this id does not exist");
        }
        libraryMapper.updateLibraryFromDto(request, library);
        libraryRepository.save(library);
        return new ResponseError("Library updated successfully");
    }

    public Object deleteLibrary(Long id) {

        if (idExists(id)) {
            return new ResponseError("Library with this id does not exist");
        }
        libraryRepository.deleteById(id);
        return new ResponseError("Deletion successfull");
    }

    public List<LibraryDTO> getAllLibraries() {

        List<LibraryDTO> response = new ArrayList<>();
        for (Library library: libraryRepository.findAll()) {
            response.add(libraryMapper.toDto(library));
        }
        return response;
    }

}
