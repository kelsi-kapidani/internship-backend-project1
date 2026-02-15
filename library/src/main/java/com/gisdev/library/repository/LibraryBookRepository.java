package com.gisdev.library.repository;

import com.gisdev.library.entity.Book;
import com.gisdev.library.entity.Library;
import com.gisdev.library.entity.LibraryBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryBookRepository extends JpaRepository<LibraryBook, Long> {

    LibraryBook findByLibraryIdAndBookId(Long libraryId, Long bookId);

    List<LibraryBook> findAllByBook(Book book);
}
