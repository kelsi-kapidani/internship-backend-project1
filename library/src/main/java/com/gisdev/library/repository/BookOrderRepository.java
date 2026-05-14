package com.gisdev.library.repository;

import com.gisdev.library.entity.BookLibraryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookOrderRepository extends JpaRepository<BookLibraryOrder, Long> {
}
