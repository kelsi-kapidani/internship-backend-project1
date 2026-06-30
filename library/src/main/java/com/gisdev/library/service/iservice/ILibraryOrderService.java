package com.gisdev.library.service.iservice;

import com.gisdev.library.dto.request.order.OrderCreateRequestDTO;
import com.gisdev.library.dto.request.order.OrderUpdateRequestDTO;
import com.gisdev.library.dto.response.order.FullOrderResponseDTO;
import com.gisdev.library.entity.LibraryOrder;

import java.util.List;

public interface ILibraryOrderService {

    Long createOrder(OrderCreateRequestDTO request);

    Long updateOrder(Long orderId, OrderUpdateRequestDTO request);

    List<FullOrderResponseDTO> getAllPendingOrders();

    List<FullOrderResponseDTO> getAllOrders();

    byte[] exportOrderPDF(Long id);

    public byte[] exportOrdersExcel(String status, Long userId);
}