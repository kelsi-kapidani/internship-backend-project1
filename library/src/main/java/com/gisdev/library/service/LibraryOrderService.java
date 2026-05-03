package com.gisdev.library.service;

import com.gisdev.library.constants.enums.Status;
import com.gisdev.library.dto.request.order.*;
import com.gisdev.library.dto.response.book.BaseBookResponseDTO;
import com.gisdev.library.dto.response.bookorder.BookOrderResponseDTO;
import com.gisdev.library.dto.response.order.FullOrderResponseDTO;
import com.gisdev.library.dto.response.user.BaseUserResponseDTO;
import com.gisdev.library.entity.*;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.repository.*;
import com.gisdev.library.service.iservice.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryOrderService implements ILibraryOrderService {

    private final LibraryOrderRepository orderRepository;
    private final ModelMapper modelMapper;

    private final IBookService bookService;
    private final ILibraryService libraryService;
    private final ILibraryUserService userService;
    private final ILibraryBookService lbService;
    private final IBookOrderService boService;

    @Override
    public void deleteWithException(String exceptionMessage, LibraryOrder order) {
        orderRepository.delete(order);
        throw new BadRequestException(exceptionMessage);
    }

    @Override
    public Long createOrder(Long id, OrderCreateRequestDTO request) {

        //create new order
        LibraryUser user = userService.getUserById(id,"User with this id does not exist");
        LibraryOrder order = LibraryOrder.builder()
                .status(Status.NE_PRITJE)
                .user(user)
                .build();
        orderRepository.save(order);
        Library library = user.getLibrary();
        //create book-orders for each item in the request
        for (BookOrderRequestDTO borequest: request.getBooks()) {
            //check for errors in the request data of each item
            //check if book requested exists
            Book book = bookService.getBookById(borequest.getBookId()).orElseThrow(() -> {
                deleteWithException("Book in the list with id" + borequest.getBookId() + "does not exist", order);
                //never reached
                return new RuntimeException();
                });
            //check if there is any strock at all of the book in the library
            LibraryBook lb = lbService.getLibraryBookByIds(library.getId(),book.getId());
            if (lb == null) {
                deleteWithException("There is no stock of book " +book.getId() + " in the user's library", order);
            }
            //check if there is enough stock
            if (lb.getStock() < borequest.getAmount()) {
                deleteWithException("There is not enough stock of book " + book.getId() + " in the user's library", order);
            }
            //create the book-order for the current item in the request
            BookOrder bo = BookOrder.builder()
                    .book(book)
                    .order(order)
                    .size(borequest.getAmount())
                    .value(borequest.getAmount() * Integer.parseInt(book.getPrice()))
                    .build();
            boService.saveBookOrder(bo);
        }
        return order.getId();
    }

    @Override
    public Long updateOrder(Long id, OrderUpdateRequestDTO request) {

        //check for errors in the request
        //bad status change in request case
        if (request.getStatus() == Status.NE_PRITJE) {
            throw new BadRequestException("You can not send order's status to pending");
        }
        //check order
        LibraryOrder order = orderRepository.findById(id).orElseThrow(() -> new BadRequestException("This order does not exist"));
        //bad order status requested for change case
        if (order.getStatus() != Status.NE_PRITJE) {
            throw new BadRequestException("This order's status cannot be changed");
        }
        //changing order to accepted status
        if (request.getStatus() == Status.PRANUAR) {
            //check if library of order/user exists
            Library library = libraryService.getLibraryById(order.getUser().getLibrary().getId(),"Could not find library of the order's user");
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
            if (request.getNote() != null) {
                order.setNote(request.getNote());
            }
        }
        order.setStatus(request.getStatus());
        orderRepository.save(order);
        return id;
    }

    @Override
    public List<FullOrderResponseDTO> getAllPendingOrders() {

        List<FullOrderResponseDTO> response = new ArrayList<>();
        for (LibraryOrder order: orderRepository.findAllByStatus(Status.NE_PRITJE)) {
            Integer sum = 0;
            LibraryUser user = order.getUser();
            List<BookOrderResponseDTO> books = new ArrayList<>();
            for (BookOrder bo: order.getBooks()) {
                sum += bo.getValue();
                books.add(new BookOrderResponseDTO(
                        modelMapper.map(bo.getBook(), BaseBookResponseDTO.class),
                        bo.getSize(),
                        bo.getValue()));
            }
            FullOrderResponseDTO orderr = new FullOrderResponseDTO(
                    order.getId(),
                    sum,
                    modelMapper.map(user, BaseUserResponseDTO.class),
                    books);
            response.add(orderr);
        }
        return response;
    }
}
