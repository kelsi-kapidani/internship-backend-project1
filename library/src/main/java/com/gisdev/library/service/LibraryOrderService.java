package com.gisdev.library.service;

import com.gisdev.library.repository.LibraryOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryOrderService {

    public final LibraryOrderRepository orderRepository;

}
