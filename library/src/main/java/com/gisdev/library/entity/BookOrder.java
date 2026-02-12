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
public class BookOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer size;
    private Integer value;

    @OneToOne
    @JoinColumn(name = "bookId")
    @ToString.Exclude
    private Book book;
    @OneToOne
    @JoinColumn(name = "orderId")
    @ToString.Exclude
    private LibraryOrder order;
}
