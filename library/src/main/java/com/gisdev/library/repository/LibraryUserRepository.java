package com.gisdev.library.repository;

import com.gisdev.library.entity.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {

    boolean existsByUsername(String username);
}
