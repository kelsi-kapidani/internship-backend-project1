package com.gisdev.library.entity;

import jakarta.persistence.*;

@Entity
public class LibraryBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
    @OneToOne
    @JoinColumn(name = "libraryId")
    private Library library;
}
