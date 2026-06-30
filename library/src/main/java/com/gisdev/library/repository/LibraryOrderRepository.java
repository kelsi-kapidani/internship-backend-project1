package com.gisdev.library.repository;

import com.gisdev.library.constants.enums.Status;
import com.gisdev.library.entity.Library;
import com.gisdev.library.entity.LibraryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryOrderRepository extends JpaRepository<LibraryOrder, Long> {

    @Query("""
    SELECT DISTINCT o
    FROM LibraryOrder o
    LEFT JOIN FETCH o.user
    LEFT JOIN FETCH o.books bo
    LEFT JOIN FETCH bo.book
    WHERE o.status = :status
    """)
    List<LibraryOrder> findAllByStatus(Status status);

    @Query("""
    SELECT DISTINCT o
    FROM LibraryOrder o
    LEFT JOIN FETCH o.user
    LEFT JOIN FETCH o.books bo
    LEFT JOIN FETCH bo.book
    """)
    List<LibraryOrder> findAllCustom();

    @Query("""
    SELECT DISTINCT o
    FROM LibraryOrder o
    LEFT JOIN FETCH o.user
    LEFT JOIN FETCH o.books bo
    LEFT JOIN FETCH bo.book
    WHERE o.user.id = :userId
    """)
    List<LibraryOrder> findAllByUserId(@Param("userId") Long userId);

    @Query("""
    SELECT o FROM LibraryOrder o
    WHERE (:status IS NULL OR o.status = :status)
      AND (:userId IS NULL OR o.user.id = :userId)
    """)
    List<LibraryOrder> findAllWithFilters(@Param("status") Status status,@Param("userId") Long userId);
}
