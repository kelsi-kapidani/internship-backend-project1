package com.gisdev.library.controller;

import com.gisdev.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BookController {

    public final BookService bookService;
}
