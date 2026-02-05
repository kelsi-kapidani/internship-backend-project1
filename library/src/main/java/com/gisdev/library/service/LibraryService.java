package com.gisdev.library.service;

import com.gisdev.library.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryService {

    public final LibraryRepository libraryRepository;

}
