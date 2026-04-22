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
import com.gisdev.library.service.iservice.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryOrderService implements ILibraryOrderService {

    public final LibraryOrderRepository orderRepository;
//    public final LibraryRepository libraryRepository;
//    public final LibraryUserRepository userRepository;
//    public final LibraryBookRepository lbRepository;
//    public final BookOrderRepository boRepository;
//    public final BookRepository bookRepository;
    public final LibraryMapper libraryMapper;

    public final IBookService bookService;
    public final ILibraryService libraryService;
    public final ILibraryUserService userService;
    public final ILibraryBookService lbService;
    public final IBookOrderService boService;

    @Override
    public Long createOrder(Long id, OrderCreateDTO request) {

        //create new order
        LibraryUser user = userService.getUserById(id).orElseThrow(() -> new BadRequestException("User with this id does not exist"));
        LibraryOrder order = LibraryOrder.builder()
                .status(Status.NE_PRITJE)
                .user(user)
                .build();
        orderRepository.save(order);
        Library library = user.getLibrary();
        //create book-orders for each item in the request
        for (OrderCreateDTO.BookOrderRequest borequest: request.books()) {
            //check for errors in the request data of each item
            //check if book requested exists
            Book book = bookService.getBookById(borequest.bookId()).orElseGet(() -> {
                orderRepository.delete(order);
                throw new BadRequestException("Book in the list with id" + borequest.bookId() + "does not exist");
            });
            //check if there is any strock at all of the book in the library
            LibraryBook lb = lbService.getLibraryBookByIds(library.getId(),book.getId());
            if (lb == null) {
                orderRepository.delete(order);
                throw new BadRequestException("There is no stock of book " +book.getId() + " in the user's library");
            }
            //check if there is enough stock
            if (lb.getStock() < borequest.amount()) {
                orderRepository.delete(order);
                throw new BadRequestException("There is not enough stock of book " + book.getId() + " in the user's library");
            }
            //create the book-order for the current item in the request
            BookOrder bo = BookOrder.builder()
                    .book(book)
                    .order(order)
                    .size(borequest.amount())
                    .value(borequest.amount() * Integer.parseInt(book.getPrice()))
                    .build();
            boService.saveBookOrder(bo);
        }
        return order.getId();
    }

    @Override
    public Long updateOrder(Long id, OrderUpdateDTO request) {

        //check for errors in the request
        //bad status change in request case
        if (request.status() == Status.NE_PRITJE) {
            throw new BadRequestException("You can not send order's status to pending");
        }
        //check order
        LibraryOrder order = orderRepository.findById(id).orElseThrow(() -> new BadRequestException("This order does not exist"));
        //bad order status requested for change case
        if (order.getStatus() != Status.NE_PRITJE) {
            throw new BadRequestException("This order's status cannot be changed");
        }
        //changing order to accepted status
        if (request.status() == Status.PRANUAR) {
            //check if library of order/user exists
            Library library = libraryService.getLibraryById(order.getUser().getLibrary().getId()).orElseThrow(() -> new BadRequestException("Could not find library of the order's user"));
            //reserve/allocate books of the order to it
            for (BookOrder bo: order.getBooks()) {
                LibraryBook currentBook = lbService.getLibraryBookByIds(library.getId(), bo.getBook().getId());
                Integer currentStock = currentBook.getStock();
                Integer currentSize = bo.getSize();
                //check if there is enough stock to fulfill order
                if(currentStock >= currentSize) {
                    currentBook.setStock(currentStock - currentSize);
                } else {
                    throw new BadRequestException("Order cannot be accepted as the stock of book " + bo.getBook().getId() + " in the library is not enough");
                }
            }
        //changing order to declined status
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
