package com.gisdev.library.repository;

import com.gisdev.library.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {

    boolean existsByName(String name);

    boolean existsById(Long id);

    @Query("""
    SELECT DISTINCT l FROM Library l
    LEFT JOIN FETCH l.users
    LEFT JOIN FETCH l.books lb
    LEFT JOIN FETCH lb.book
    WHERE (:name IS NULL OR l.name = :name)
      AND (:address IS NULL OR l.address = :address)
    """)
    List<Library> findAllWithFilters(@Param("name") String name, @Param("address") String address);
}
