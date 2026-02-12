package com.gisdev.library.entity;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Section;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    private Section section;

    private String price;
    private LocalDate yearOfPublication;

    @OneToOne
    @ToString.Exclude
    private BookOrder order;
    @OneToOne
    @ToString.Exclude
    private LibraryBook library;
}
