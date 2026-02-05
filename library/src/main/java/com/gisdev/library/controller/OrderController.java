package com.gisdev.library.controller;

import com.gisdev.library.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OrderController {

    public final OrderService orderService;
}
