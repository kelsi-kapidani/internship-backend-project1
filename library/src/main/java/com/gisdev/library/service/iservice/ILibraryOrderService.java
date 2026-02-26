package com.gisdev.library.service.iservice;

import com.gisdev.library.dto.request.order.OrderCreateDTO;
import com.gisdev.library.dto.request.order.OrderUpdateDTO;
import com.gisdev.library.dto.response.order.OrderDTO;

import java.util.List;

public interface ILibraryOrderService {

    Long createOrder(Long userId, OrderCreateDTO request);

    Long updateOrder(Long orderId, OrderUpdateDTO request);

    List<OrderDTO> getAllPendingOrders();
}