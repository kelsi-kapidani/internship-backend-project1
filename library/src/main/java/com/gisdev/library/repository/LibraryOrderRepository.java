package com.gisdev.library.repository;

import com.gisdev.library.constants.enums.Status;
import com.gisdev.library.entity.LibraryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryOrderRepository extends JpaRepository<LibraryOrder, Long> {

    List<LibraryOrder> findAllByStatus(Status status);
}
