package com.gisdev.library.service;

import com.gisdev.library.dto.request.librarybook.LibraryBookAddDTO;
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
import com.gisdev.library.service.iservice.ILibraryBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LibraryBookService implements ILibraryBookService {

    public final LibraryBookRepository lbRepository;
    public final LibraryRepository libraryRepository;
    public final BookRepository bookRepository;
    public final LibraryMapper libraryMapper;

    @Override
    public Long addListOfBooks(LibraryBookAddDTO request, Long libraryId) {

        Library library = libraryRepository.findById(libraryId).orElse(null);
        if (library == null) {
            throw new BadRequestException("Library submitted does not exist");
        }
        for (LibraryBookAddDTO.BookAddDTO book: request.books()) {
            Book rbook = bookRepository.findById(book.id()).orElse(null);
            if (book == null) {
                throw new BadRequestException("Book with id " + book.id() + " in the list does not exist");
            }
            LibraryBook lb = lbRepository.findByLibraryIdAndBookId(libraryId, book.id());
            if (lb == null) {
                LibraryBook newlb = LibraryBook.builder()
                        .book(rbook)
                        .library(library)
                        .stock(book.amount())
                        .build();
                lbRepository.save(newlb);
            } else {
                lb.setStock(lb.getStock() + book.amount());
                lbRepository.save(lb);
            }
        }
        return libraryId;
    }

    @Override
    public List<LibraryBookStockDTO> getAllBookStocks() {

        List<LibraryBookStockDTO> result = new ArrayList<>();
        List<Book> books = bookRepository.findAll();
        for (Book book: books) {
            List<LibraryBook> lbs = lbRepository.findAllByBook(book);
            List<LibraryStockDTO> libstock = new ArrayList<>();
            for (LibraryBook lb: lbs) {
                libstock.add(new LibraryStockDTO(lb.getLibrary().getId(), lb.getLibrary().getName(), lb.getStock()));
            }
            result.add(new LibraryBookStockDTO(libraryMapper.toBookDto(book), libstock));
        }
        return result;
    }

}
