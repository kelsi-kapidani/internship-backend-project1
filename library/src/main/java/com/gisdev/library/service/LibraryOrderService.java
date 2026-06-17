package com.gisdev.library.service;

import com.gisdev.library.constants.enums.Status;
import com.gisdev.library.dto.request.order.*;
import com.gisdev.library.dto.response.book.BaseBookResponseDTO;
import com.gisdev.library.dto.response.bookorder.BookOrderResponseDTO;
import com.gisdev.library.dto.response.library.FullLibraryResponseDTO;
import com.gisdev.library.dto.response.order.FullOrderResponseDTO;
import com.gisdev.library.dto.response.user.BaseUserResponseDTO;
import com.gisdev.library.entity.*;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.repository.*;
import com.gisdev.library.service.iservice.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
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
    private final IAuthService authService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(OrderCreateRequestDTO request) {

        LibraryUser user = authService.getUserByToken();
        LibraryOrder order = buildAndSaveLibraryOrder(user);

        for (BookOrderRequestDTO boRequest : request.getBooks()) {
            Book book = validateBook(boRequest, user.getLibrary());
            buildAndSaveBookOrder(boRequest, book, order);
        }

        return order.getId();
    }

    private void buildAndSaveBookOrder(BookOrderRequestDTO boRequest, Book book, LibraryOrder order) {
        BookLibraryOrder bo = BookLibraryOrder.builder()
                .book(book)
                .order(order)
                .size(boRequest.getAmount())
                .value(boRequest.getAmount() * Integer.parseInt(book.getPrice()))
                .build();
        boService.saveBookOrder(bo);
    }

    private LibraryOrder buildAndSaveLibraryOrder(LibraryUser user) {
        LibraryOrder order = LibraryOrder.builder()
                .status(Status.NE_PRITJE)
                .user(user)
                .build();
        orderRepository.save(order);
        return order;
    }

    private Book validateBook(BookOrderRequestDTO boRequest, Library library) {
        Book book = bookService.getBookById(boRequest.getBookId()).orElseThrow(() -> new BadRequestException("Book in the list with id" + boRequest.getBookId() + "does not exist"));
        LibraryBook lb = lbService.getLibraryBookByIds(library.getId(), book.getId());


        if (lb == null) {
            throw new BadRequestException("Book with ID: " + book.getId() + " is not present in this library!");
        }

        if (lb.getStock() < boRequest.getAmount()) {
            throw new BadRequestException("There is not enough stock of book " + book.getId() + " in the user's library");
        }
        return book;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long updateOrder(Long id, OrderUpdateRequestDTO request) {
        LibraryOrder order = orderRepository.findById(id).orElseThrow(() -> new BadRequestException("This order does not exist"));
        validateUpdateRequest(request, order);

        if (request.getStatus() == Status.PRANUAR) {
            Library library = libraryService.getLibraryById(order.getUser().getLibrary().getId(),"Could not find library of the order's user");

            for (BookLibraryOrder bo: order.getBooks()) {
                updateStock(library, bo);
            }
        } else {
            if (request.getNote() != null) {
                order.setNote(request.getNote());
            }
        }
        order.setStatus(request.getStatus());
        orderRepository.save(order);
        return id;
    }

    private void validateUpdateRequest(OrderUpdateRequestDTO request, LibraryOrder order) {
        if (request.getStatus() == Status.NE_PRITJE) {
            throw new BadRequestException("You can not send order's status to pending");
        }
        if (order.getStatus() != Status.NE_PRITJE) {
            throw new BadRequestException("This order's status cannot be changed");
        }
    }

    private void updateStock(Library library, BookLibraryOrder bo) {
        LibraryBook currentBook = lbService.getLibraryBookByIds(library.getId(), bo.getBook().getId());
        Integer currentStock = currentBook.getStock();
        Integer currentSize = bo.getSize();

        if(currentStock >= currentSize) {
            currentBook.setStock(currentStock - currentSize);
        } else {
            throw new BadRequestException("Order cannot be accepted as the stock of book " + bo.getBook().getId() + " in the library is not enough");
        }
    }

    @Override
    public List<FullOrderResponseDTO> getAllPendingOrders() {

        List<FullOrderResponseDTO> response = new ArrayList<>();
        for (LibraryOrder order: orderRepository.findAllByStatus(Status.NE_PRITJE)) {
            LibraryUser user = order.getUser();

            List<BookOrderResponseDTO> books = new ArrayList<>();
            int sum = fillBooksList(order, books);

            mapAndAddOrder(order.getId(), sum, user, books, response);
        }
        return response;
    }

    private void mapAndAddOrder(Long id, int sum, LibraryUser user, List<BookOrderResponseDTO> books, List<FullOrderResponseDTO> response) {
        FullOrderResponseDTO orderResponse = new FullOrderResponseDTO(
                id,
                sum,
                modelMapper.map(user, BaseUserResponseDTO.class),
                books);
        response.add(orderResponse);
    }

    private int fillBooksList(LibraryOrder order, List<BookOrderResponseDTO> books) {
        int sum = 0;

        for (BookLibraryOrder bo: order.getBooks()) {
            sum += bo.getValue();
            books.add(new BookOrderResponseDTO(
                    modelMapper.map(bo.getBook(), BaseBookResponseDTO.class),
                    bo.getSize(),
                    bo.getValue()));
        }

        return sum;
    }

    @Override
    public List<FullOrderResponseDTO> getAllOrders() {

        LibraryUser currentUser = authService.getUserByToken();
        List<FullOrderResponseDTO> response = new ArrayList<>();
        List<LibraryOrder> poolOfOrders;

        if (currentUser.getRole().name().equals("ADMIN")) {
            poolOfOrders = orderRepository.findAllCustom();
        } else {
            poolOfOrders = orderRepository.findAllByUserId(currentUser.getId());
        }
        for (LibraryOrder order: poolOfOrders) {
            LibraryUser user = order.getUser();

            List<BookOrderResponseDTO> books = new ArrayList<>();
            int sum = fillBooksList(order, books);

            mapAndAddOrder(order.getId(), sum, user, books, response);
        }
        return response;
    }

}
