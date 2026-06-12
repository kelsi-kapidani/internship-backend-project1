package com.gisdev.library.repository;

import com.gisdev.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query(
            value = "SELECT Count(*)>0 FROM book WHERE title = :title",
            nativeQuery = true
    )
    boolean existsByTitle(@Param("title") String title);

    //"x3 text block that allows multi-lines -> better readability
    @Query("""
    SELECT DISTINCT b FROM Book b
    LEFT JOIN FETCH b.libraries lb
    LEFT JOIN FETCH lb.library
    """)
    List<Book> findAllWithLibraryBooks();
/*
    @Query("""
        SELECT DISTINCT b FROM Book b
        JOIN FETCH b.libraries lb
        JOIN FETCH lb.library l
        WHERE l.id = :libraryId
        """)
    List<Book> findAllByLibraryId(@Param("libraryId") Long libraryId);
*/
}
