package com.gisdev.library.service;

import com.gisdev.library.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    public final OrderRepository orderRepository;

}
