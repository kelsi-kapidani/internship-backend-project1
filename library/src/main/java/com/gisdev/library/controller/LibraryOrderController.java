package com.gisdev.library.controller;

import com.gisdev.library.dto.request.order.OrderCreateDTO;
import com.gisdev.library.dto.request.order.OrderUpdateDTO;
import com.gisdev.library.dto.response.order.OrderDTO;
import com.gisdev.library.service.LibraryOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class LibraryOrderController {

    public final LibraryOrderService orderService;

    @PostMapping("/create/{id}")
    public ResponseEntity<Long> createOrder(@PathVariable Long id, @Valid @RequestBody OrderCreateDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(id, request));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Long> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderUpdateDTO request) {

        return ResponseEntity.ok(orderService.updateOrder(id, request));
    }

    @GetMapping("/pending")
    public List<OrderDTO> getAllPendingOrders() {

        return orderService.getAllPendingOrders();
    }

}
