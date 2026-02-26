package com.gisdev.library.service;

import com.gisdev.library.constants.enums.Status;
import com.gisdev.library.dto.request.order.OrderCreateDTO;
import com.gisdev.library.dto.request.order.OrderUpdateDTO;
import com.gisdev.library.dto.response.bookorder.BookOrderDTO;
import com.gisdev.library.dto.response.order.OrderDTO;
import com.gisdev.library.entity.*;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.mapper.LibraryMapper;
import com.gisdev.library.repository.*;
import com.gisdev.library.service.iservice.ILibraryOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryOrderService implements ILibraryOrderService {

    public final LibraryOrderRepository orderRepository;
    public final LibraryUserRepository userRepository;
    public final LibraryBookRepository lbRepository;
    public final BookOrderRepository boRepository;
    public final BookRepository bookRepository;
    public final LibraryMapper libraryMapper;

    @Override
    public Long createOrder(Long id, OrderCreateDTO request) {

        LibraryUser user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new BadRequestException("User with this id does not exist");
        }
        LibraryOrder order = LibraryOrder.builder()
                .status(Status.NE_PRITJE)
                .user(user)
                .build();
        orderRepository.save(order);
        Library library = user.getLibrary();
        for (OrderCreateDTO.BookOrderRequest borequest: request.books()) {
            Book book = bookRepository.findById(borequest.bookId()).orElse(null);
            if (book == null) {
                throw new BadRequestException("Book in the list with id" + borequest.bookId()+ "does not exist");
            }
            LibraryBook lb = lbRepository.findByLibraryIdAndBookId(library.getId(),book.getId());
            if (lb == null) {
                throw new BadRequestException("There is no stock of book" +book.getId() + "in the user's library");
            }
            if (lb.getStock()< borequest.amount()) {
                throw new BadRequestException("There is not enough stock of book" + book.getId() + "in the user's library");
            }
            BookOrder bo = BookOrder.builder()
                    .book(book)
                    .order(order)
                    .size(borequest.amount())
                    .value(borequest.amount() * Integer.parseInt(book.getPrice()))
                    .build();
            boRepository.save(bo);
        }
        return order.getId();
    }

    @Override
    public Long updateOrder(Long id, OrderUpdateDTO request) {

        LibraryOrder order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new BadRequestException("This order does not exist");
        }
        order.setStatus(request.status());
        if (request.note() != null) {
            order.setNote(request.note());
        }
        orderRepository.save(order);
        return id;
    }

    @Override
    public List<OrderDTO> getAllPendingOrders() {

        List<OrderDTO> response = new ArrayList<>();
        for (LibraryOrder order: orderRepository.findAllByStatus(Status.NE_PRITJE)) {
            Integer sum = 0;
            LibraryUser user = order.getUser();
            List<BookOrderDTO> books = new ArrayList<>();
            for (BookOrder bo: order.getBooks()) {
                sum += bo.getValue();
                books.add(new BookOrderDTO(
                        libraryMapper.toBookDto(bo.getBook()),
                        bo.getSize(),
                        bo.getValue()));
            }
            OrderDTO orderr = new OrderDTO(
                    order.getId(),
                    sum,
                    libraryMapper.toUserDto(user),
                    books);
            response.add(orderr);
        }
        return response;
    }
}
