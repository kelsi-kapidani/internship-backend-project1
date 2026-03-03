package com.gisdev.library.repository;

import com.gisdev.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    //  previous derived query version
    //  boolean existsByTitle(String title);
    @Query(
            value = "SELECT Count(*)>0 FROM book WHERE title = :title",
            nativeQuery = true
    )
    boolean existsByTitle(@Param("title") String title);
}
