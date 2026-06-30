package com.gisdev.library.controller;

import com.gisdev.library.dto.request.order.OrderCreateRequestDTO;
import com.gisdev.library.dto.request.order.OrderUpdateRequestDTO;
import com.gisdev.library.dto.response.order.FullOrderResponseDTO;
import com.gisdev.library.service.iservice.ILibraryOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class LibraryOrderController {

    private final ILibraryOrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<Long> createOrder(@Valid @RequestBody OrderCreateRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<Long> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderUpdateRequestDTO request) {

        return ResponseEntity.ok(orderService.updateOrder(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all/pending")
    public List<FullOrderResponseDTO> getAllPendingOrders() {

        return orderService.getAllPendingOrders();
    }

    @GetMapping("/all")
    public List<FullOrderResponseDTO> getAllOrders() {

        return orderService.getAllOrders();
    }

    @GetMapping("/export/{id}")
    public ResponseEntity<byte[]> exportOrder(@PathVariable Long id) {

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=order_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(orderService.exportOrderPDF(id));
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportOrders(@RequestParam(required = false) String status, @RequestParam(required = false) Long userId) {

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=pending-orders.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(orderService.exportOrdersExcel(status, userId));
    }
}
