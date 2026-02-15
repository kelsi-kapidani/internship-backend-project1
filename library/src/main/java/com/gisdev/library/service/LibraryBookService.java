package com.gisdev.library.service;


import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.response.BookWithLibraryStockResponse;
import com.gisdev.library.dto.response.LibraryStock;
import com.gisdev.library.entity.Book;
import com.gisdev.library.entity.Library;
import com.gisdev.library.entity.LibraryBook;
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

    public Object addListOfBooks(List<Long> idOfBooks, Long libraryId) {

        Library library = libraryRepository.findById(libraryId).orElse(null);
        if (library == null) {
            return new ResponseError("Library submitted does not exist");
        }
        for (Long bookId: idOfBooks) {
            Book book = bookRepository.findById(bookId).orElse(null);
            if (book == null) {
                return new ResponseError("Book with id " + bookId + " in the list does not exist");
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
            }
        }
        return new ResponseError("Books added successfully to library");
    }

    public List<BookWithLibraryStockResponse> getAllBookStocks() {

        List<BookWithLibraryStockResponse> result = new ArrayList<>();
        List<Book> books = bookRepository.findAll();
        for (Book book: books) {
            List<LibraryBook> lbs = lbRepository.findAllByBook(book);
            List<LibraryStock> libstock = new ArrayList<>();
            for (LibraryBook lb: lbs) {
                libstock.add(new LibraryStock(lb.getLibrary(), lb.getStock()));
            }
            result.add(new BookWithLibraryStockResponse(book, libstock));
        }
        return result;
    }

}
