package com.gisdev.library.service.iservice;

import com.gisdev.library.dto.request.library.LibraryCUDTO;
import com.gisdev.library.dto.response.library.LibraryDTO;
import com.gisdev.library.entity.Library;

import java.util.List;
import java.util.Optional;

public interface ILibraryService {

    boolean nameExists(String name);

    boolean idExists(Long id);

    Optional<Library> getLibraryById(Long id);

    Long createLibrary(LibraryCUDTO request);

    Long updateLibrary(Long id, LibraryCUDTO request);

    Long deleteLibrary(Long id);

    List<LibraryDTO> getAllLibraries(String name, String address);
}
