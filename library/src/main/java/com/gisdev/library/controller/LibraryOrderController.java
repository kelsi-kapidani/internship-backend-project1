package com.gisdev.library.controller;

import com.gisdev.library.dto.request.order.OrderCreateRequestDTO;
import com.gisdev.library.dto.request.order.OrderUpdateRequestDTO;
import com.gisdev.library.dto.response.order.FullOrderResponseDTO;
import com.gisdev.library.service.iservice.ILibraryOrderService;
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

    private final ILibraryOrderService orderService;

    @PostMapping("/create/{id}")
    public ResponseEntity<Long> createOrder(@PathVariable Long id, @Valid @RequestBody OrderCreateRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(id, request));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Long> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderUpdateRequestDTO request) {

        return ResponseEntity.ok(orderService.updateOrder(id, request));
    }

    @GetMapping("/pending")
    public List<FullOrderResponseDTO> getAllPendingOrders() {

        return orderService.getAllPendingOrders();
    }

}
