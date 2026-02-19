package com.gisdev.library.service;

import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.response.BookLibraryStockDTO;
import com.gisdev.library.dto.response.BookLibraryStockDTO.LibraryStock;
import com.gisdev.library.entity.Book;
import com.gisdev.library.entity.Library;
import com.gisdev.library.entity.LibraryBook;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.mapper.LibraryMapper;
import com.gisdev.library.repository.BookRepository;
import com.gisdev.library.repository.LibraryBookRepository;
import com.gisdev.library.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LibraryBookService {

    public final LibraryBookRepository lbRepository;
    public final LibraryRepository libraryRepository;
    public final BookRepository bookRepository;
    public final LibraryMapper libraryMapper;

    public Object addListOfBooks(List<Long> idOfBooks, Long libraryId) {

        Library library = libraryRepository.findById(libraryId).orElse(null);
        if (library == null) {
            return new BadRequestException("Library submitted does not exist");
        }
        for (Long bookId: idOfBooks) {
            Book book = bookRepository.findById(bookId).orElse(null);
            if (book == null) {
                return new BadRequestException("Book with id " + bookId + " in the list does not exist");
            }
            LibraryBook lb = lbRepository.findByLibraryIdAndBookId(libraryId, book.getId());
            if (lb == null) {
                LibraryBook newlb = LibraryBook.builder()
                        .book(book)
                        .library(library)
                        .stock(1)
                        .build();
                lbRepository.save(newlb);
            } else {
                lb.setStock(lb.getStock() + 1);
                lbRepository.save(lb);
            }
        }
        return new ResponseError("Books added successfully to library");
    }

    public List<BookLibraryStockDTO> getAllBookStocks() {

        List<BookLibraryStockDTO> result = new ArrayList<>();
        List<Book> books = bookRepository.findAll();
        for (Book book: books) {
            List<LibraryBook> lbs = lbRepository.findAllByBook(book);
            List<LibraryStock> libstock = new ArrayList<>();
            for (LibraryBook lb: lbs) {
                libstock.add(new LibraryStock(lb.getLibrary().getId(), lb.getLibrary().getName(), lb.getStock()));
            }
            result.add(new BookLibraryStockDTO(libraryMapper.toBookDto(book), libstock));
        }
        return result;
    }

}
