package com.gisdev.library.service.iservice;

import com.gisdev.library.dto.request.library.LibraryCUDTO;
import com.gisdev.library.dto.response.library.LibraryDTO;

import java.util.List;

public interface ILibraryService {

    boolean nameExists(String name);

    boolean idExists(Long id);

    Long createLibrary(LibraryCUDTO request);

    Long updateLibrary(Long id, LibraryCUDTO request);

    Long deleteLibrary(Long id);

    List<LibraryDTO> getAllLibraries();
}
