package com.gisdev.library.service;

import com.gisdev.library.repository.LibraryRepository;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {

    public final LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }
}
