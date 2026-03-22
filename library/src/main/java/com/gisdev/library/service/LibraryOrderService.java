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
    public final LibraryRepository libraryRepository;
    public final LibraryUserRepository userRepository;
    public final LibraryBookRepository lbRepository;
    public final BookOrderRepository boRepository;
    public final BookRepository bookRepository;
    public final LibraryMapper libraryMapper;

    @Override
    public Long createOrder(Long id, OrderCreateDTO request) {

        LibraryUser user = userRepository.findById(id).orElseThrow(() -> new BadRequestException("User with this id does not exist"));
        LibraryOrder order = LibraryOrder.builder()
                .status(Status.NE_PRITJE)
                .user(user)
                .build();
        orderRepository.save(order);
        Library library = user.getLibrary();
        for (OrderCreateDTO.BookOrderRequest borequest: request.books()) {
            Book book = bookRepository.findById(borequest.bookId()).orElseGet(() -> {
                orderRepository.delete(order);
                throw new BadRequestException("Book in the list with id" + borequest.bookId() + "does not exist");
            });
            LibraryBook lb = lbRepository.findByLibraryIdAndBookId(library.getId(),book.getId());
            if (lb == null) {
                orderRepository.delete(order);
                throw new BadRequestException("There is no stock of book " +book.getId() + " in the user's library");
            }
            if (lb.getStock() < borequest.amount()) {
                orderRepository.delete(order);
                throw new BadRequestException("There is not enough stock of book " + book.getId() + " in the user's library");
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

        if (request.status() == Status.NE_PRITJE) {
            throw new BadRequestException("You can not send order's status to pending");
        }
        LibraryOrder order = orderRepository.findById(id).orElseThrow(() -> new BadRequestException("This order does not exist"));
        if (order.getStatus() != Status.NE_PRITJE) {
            throw new BadRequestException("This order's status cannot be changed");
        }
        if (request.status() == Status.PRANUAR) {
            Library library = libraryRepository.findById(order.getUser().getId()).orElseThrow(() -> new BadRequestException("Could not find library of the order's user"));
            for (BookOrder bo: order.getBooks()) {
                LibraryBook currentBook = lbRepository.findByLibraryIdAndBookId(library.getId(), bo.getBook().getId());
                Integer currentStock = currentBook.getStock();
                Integer currentSize = bo.getSize();
                if(currentStock >= currentSize) {
                    currentBook.setStock(currentStock - currentSize);
                } else {
                    throw new BadRequestException("Order cannot be accepted as the stock of book " + bo.getBook().getId() + " in the library is not enough");
                }
            }
        } else {
            if (request.note() != null) {
                order.setNote(request.note());
            }
        }
        order.setStatus(request.status());
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
