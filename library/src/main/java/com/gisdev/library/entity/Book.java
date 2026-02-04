package com.gisdev.library.entity;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Section;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private Genre genre;
    private Section section;
    private String price;
    private LocalDate yearOfPublication;

    @OneToOne
    private BookOrder order;
    @OneToOne
    private LibraryBook library;
}
