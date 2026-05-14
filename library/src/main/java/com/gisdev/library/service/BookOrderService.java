package com.gisdev.library.service;

import com.gisdev.library.entity.BookLibraryOrder;
import com.gisdev.library.repository.BookOrderRepository;
import com.gisdev.library.service.iservice.IBookOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookOrderService implements IBookOrderService {

    public final BookOrderRepository boRepository;

    @Override
    public void saveBookOrder (BookLibraryOrder bo) {
        boRepository.save(bo);
    }
}
