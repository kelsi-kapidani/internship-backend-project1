package com.gisdev.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class BookOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer size;
    private Integer value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    @ToString.Exclude
    private Book book;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libraryorderId")
    @ToString.Exclude
    private LibraryOrder order;
}
