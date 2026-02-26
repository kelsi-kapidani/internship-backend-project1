package com.gisdev.library.service;

import com.gisdev.library.dto.request.library.LibraryCUDTO;
import com.gisdev.library.dto.response.library.LibraryDTO;
import com.gisdev.library.entity.Library;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.mapper.LibraryMapper;
import com.gisdev.library.repository.LibraryRepository;
import com.gisdev.library.service.iservice.ILibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService implements ILibraryService {

    public final LibraryRepository libraryRepository;
    public final LibraryMapper libraryMapper;

    @Override
    public boolean nameExists(String username) {
        return libraryRepository.existsByName(username);
    }

    @Override
    public boolean idExists(Long id) {
        return libraryRepository.existsById(id);
    }

    @Override
    public Long createLibrary(LibraryCUDTO request) {

        if (nameExists(request.name())) {
            throw new BadRequestException("Library with this name already exists");
        }
        Library library = libraryMapper.toEntity(request);
        libraryRepository.save(library);
        return library.getId();
    }

    @Override
    public Long updateLibrary(Long id, LibraryCUDTO request) {

        Library library = libraryRepository.findById(id).orElse(null);
        if (library == null) {
            throw new BadRequestException("Library with this id does not exist");
        }
        libraryMapper.updateLibraryFromDto(request, library);
        libraryRepository.save(library);
        return id;
    }

    @Override
    public Long deleteLibrary(Long id) {

        if (idExists(id)) {
            throw new BadRequestException("Library with this id does not exist");
        }
        libraryRepository.deleteById(id);
        return id;
    }

    @Override
    public List<LibraryDTO> getAllLibraries() {

        List<LibraryDTO> response = new ArrayList<>();
        for (Library library: libraryRepository.findAll()) {
            response.add(libraryMapper.toDto(library));
        }
        return response;
    }

}
