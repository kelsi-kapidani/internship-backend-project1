package com.gisdev.library.repository;

import com.gisdev.library.entity.LibraryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryOrderRepository extends JpaRepository<LibraryOrder, Long> {
}
