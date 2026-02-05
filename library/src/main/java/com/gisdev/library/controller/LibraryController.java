package com.gisdev.library.controller;

import com.gisdev.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class LibraryController {

    public final LibraryService libraryService;
}
