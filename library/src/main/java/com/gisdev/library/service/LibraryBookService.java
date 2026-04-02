package com.gisdev.library.service;

import com.gisdev.library.dto.request.librarybook.LibraryBookDTO;
import com.gisdev.library.dto.response.librarybook.LibraryBookStockDTO;
import com.gisdev.library.dto.response.librarybook.LibraryStockDTO;
import com.gisdev.library.entity.Book;
import com.gisdev.library.entity.Library;
import com.gisdev.library.entity.LibraryBook;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.mapper.LibraryMapper;
import com.gisdev.library.repository.BookRepository;
import com.gisdev.library.repository.LibraryBookRepository;
import com.gisdev.library.repository.LibraryRepository;
import com.gisdev.library.service.iservice.IBookService;
import com.gisdev.library.service.iservice.ILibraryBookService;
import com.gisdev.library.service.iservice.ILibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LibraryBookService implements ILibraryBookService {

    public final LibraryBookRepository lbRepository;
//    public final LibraryRepository libraryRepository;
//    public final BookRepository bookRepository;
    public final LibraryMapper libraryMapper;

    public final ILibraryService libraryService;
    public final IBookService bookService;

    @Override
    public LibraryBook getLibraryBookByIds(Long bookId, Long libraryId) {
        return lbRepository.findByLibraryIdAndBookId(bookId, libraryId);
    }

    @Override
    public Long addListOfBooks(LibraryBookDTO request, Long libraryId) {

        Library library = libraryService.getLibraryById(libraryId).orElseThrow(() -> new BadRequestException("Library submitted does not exist"));
        for (LibraryBookDTO.BookAddDTO book: request.books()) {
            Book rbook = bookService.getBookById(book.id()).orElseThrow(() -> new BadRequestException("Book with id " + book.id() + " in the list does not exist"));
            LibraryBook lb = lbRepository.findByLibraryIdAndBookId(libraryId, book.id());
            if (lb == null) {
                lb = LibraryBook.builder()
                        .book(rbook)
                        .library(library)
                        .stock(book.amount())
                        .build();
            } else {
                lb.setStock(lb.getStock() + book.amount());
            }
            lbRepository.save(lb);
        }
        return libraryId;
    }

    @Override
    public List<LibraryBookStockDTO> getAllBookStocks() {

        List<LibraryBookStockDTO> result = new ArrayList<>();
        List<Book> books = bookService.getAllWithLibraryBooks();
        for (Book book: books) {
            List<LibraryStockDTO> libstock = book.getLibraries().stream()
                    .map(lb -> new LibraryStockDTO(
                            lb.getLibrary().getId(),
                            lb.getLibrary().getName(),
                            lb.getStock()
                    ))
                    .toList();
            LibraryBookStockDTO dto = new LibraryBookStockDTO(
                    libraryMapper.toBookDto(book),
                    libstock
            );

            result.add(new LibraryBookStockDTO(libraryMapper.toBookDto(book), libstock));
        }
        return result;
    }

}
