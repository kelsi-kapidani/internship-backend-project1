package com.gisdev.library.service;

import com.gisdev.library.constants.enums.Status;
import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.request.OrderCreateDTO;
import com.gisdev.library.dto.request.OrderUpdateDTO;
import com.gisdev.library.entity.*;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.repository.BookOrderRepository;
import com.gisdev.library.repository.LibraryBookRepository;
import com.gisdev.library.repository.LibraryOrderRepository;
import com.gisdev.library.repository.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryOrderService {

    public final LibraryOrderRepository orderRepository;
    public final LibraryUserRepository userRepository;
    public final LibraryBookRepository lbRepository;
    public final BookOrderRepository boRepository;

    public Object createOrder(Long id, OrderCreateDTO request) {

        LibraryUser user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new BadRequestException("User with this id does not exist");
        }
        LibraryOrder order = LibraryOrder.builder()
                .status(Status.NE_PRITJE)
                .user(user)
                .build();
        orderRepository.save(order);
        Library library = user.getLibrary();
        for (OrderCreateDTO.BookOrderRequest borequest: request.books()) {
            LibraryBook lb = lbRepository.findByLibraryIdAndBookId(library.getId(), borequest.book().getId());
            if (lb == null) {
                return new BadRequestException("There is no stock of book" + borequest.book().getId() + "in the user's library");
            }
            if (lb.getStock()< borequest.amount()) {
                return new BadRequestException("There is not enough stock of book" + borequest.book().getId() + "in the user's library");
            }
            BookOrder bo = BookOrder.builder()
                    .book(borequest.book())
                    .order(order)
                    .size(borequest.amount())
                    .value(borequest.amount() + Integer.parseInt(borequest.book().getPrice()))
                    .build();
            boRepository.save(bo);
        }
        return new ResponseError("Order created successfully");
    }

    public Object updateOrder(Long id, OrderUpdateDTO request) {

        LibraryOrder order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return new BadRequestException("This order does not exist");
        }
        order.setStatus(request.status());
        if (request.note() != null) {
            order.setNote(request.note());
        }
        return orderRepository.save(order);
    }

    public List<LibraryOrder> getAllPendingOrders() {

        return orderRepository.findAllByStatus(Status.NE_PRITJE);
    }
}
