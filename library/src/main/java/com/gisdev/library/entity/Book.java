package com.gisdev.library.entity;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Section;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @OneToMany(mappedBy = "book")
    @ToString.Exclude
    private List<BookOrder> orders;
    @OneToMany(mappedBy = "book")
    @ToString.Exclude
    private List<LibraryBook> libraries;
}
