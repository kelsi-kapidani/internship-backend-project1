package com.gisdev.library.controller;

import com.gisdev.library.dto.request.OrderCreateDTO;
import com.gisdev.library.dto.request.OrderUpdateDTO;
import com.gisdev.library.entity.LibraryOrder;
import com.gisdev.library.service.LibraryOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class LibraryOrderController {

    public final LibraryOrderService orderService;

    @PostMapping("create/{id}")
    public Object createOrder(@PathVariable Long id, @Valid @RequestBody OrderCreateDTO request) {

        return orderService.createOrder(id, request);
    }

    @PatchMapping("update/{id}")
    public Object updateOrder(@PathVariable Long id, @Valid @RequestBody OrderUpdateDTO request) {

        return orderService.updateOrder(id, request);
    }

    @GetMapping("/pending")
    public List<LibraryOrder> getAllPendingOrders() {

        return orderService.getAllPendingOrders();
    }

}
