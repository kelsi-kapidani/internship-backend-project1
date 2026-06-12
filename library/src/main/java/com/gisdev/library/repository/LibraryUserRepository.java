package com.gisdev.library.repository;

import com.gisdev.library.entity.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {

    boolean existsByUsername(String username);

    boolean existsById(Long id);

    Optional<LibraryUser> findByUsername(String username);


}
