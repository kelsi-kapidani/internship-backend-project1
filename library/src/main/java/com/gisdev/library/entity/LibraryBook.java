package com.gisdev.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class LibraryBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "bookId")
    @ToString.Exclude
    private Book book;
    @OneToOne
    @JoinColumn(name = "libraryId")
    @ToString.Exclude
    private Library library;

}
