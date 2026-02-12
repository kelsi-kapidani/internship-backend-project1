package com.gisdev.library.controller;

import com.gisdev.library.service.LibraryOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class LibraryOrderController {

    public final LibraryOrderService orderService;
}
