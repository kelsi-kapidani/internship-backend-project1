package com.gisdev.library.service;

import com.gisdev.library.dto.request.librarybook.BaseLibraryBookRequestDTO;
import com.gisdev.library.dto.request.librarybook.LibraryBookAmountRequestDTO;
import com.gisdev.library.dto.response.book.BaseBookResponseDTO;
import com.gisdev.library.dto.response.librarybook.LibraryBookResponseDTO;
import com.gisdev.library.dto.response.librarybook.LibraryBookStockResponseDTO;
import com.gisdev.library.entity.Book;
import com.gisdev.library.entity.Library;
import com.gisdev.library.entity.LibraryBook;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.repository.LibraryBookRepository;
import com.gisdev.library.service.iservice.IBookService;
import com.gisdev.library.service.iservice.ILibraryBookService;
import com.gisdev.library.service.iservice.ILibraryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LibraryBookService implements ILibraryBookService {

    private final LibraryBookRepository lbRepository;
    private final ModelMapper modelMapper;

    private final ILibraryService libraryService;
    private final IBookService bookService;

    @Override
    public LibraryBook getLibraryBookByIds(Long bookId, Long libraryId) {
        return lbRepository.findByLibraryIdAndBookId(bookId, libraryId);
    }

    @Override
    public Long addListOfBooks(BaseLibraryBookRequestDTO request, Long libraryId) {

        Library library = libraryService.getLibraryById(libraryId,"Library submitted does not exist");
        for (LibraryBookAmountRequestDTO book: request.getBooks()) {
            Book rbook = bookService.getBookById(book.getId()).orElseThrow(() -> new BadRequestException("Book with id " + book.getId() + " in the list does not exist"));
            LibraryBook lb = lbRepository.findByLibraryIdAndBookId(libraryId, book.getId());
            if (lb == null) {
                lb = LibraryBook.builder()
                        .book(rbook)
                        .library(library)
                        .stock(book.getAmount())
                        .build();
            } else {
                lb.setStock(lb.getStock() + book.getAmount());
            }
            lbRepository.save(lb);
        }
        return libraryId;
    }

    @Override
    public List<LibraryBookResponseDTO> getAllBookStocks() {

        List<LibraryBookResponseDTO> result = new ArrayList<>();
        List<Book> books = bookService.getAllWithLibraryBooks();
        for (Book book: books) {
            List<LibraryBookStockResponseDTO> libstock = book.getLibraries().stream()
                    .map(lb -> new LibraryBookStockResponseDTO(
                            lb.getLibrary().getId(),
                            lb.getLibrary().getName(),
                            lb.getStock()
                    ))
                    .toList();
            result.add(new LibraryBookResponseDTO(modelMapper.map(book, BaseBookResponseDTO.class), libstock));
        }
        return result;
    }

}
