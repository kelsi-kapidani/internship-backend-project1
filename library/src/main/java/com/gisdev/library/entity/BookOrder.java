package com.gisdev.library.entity;

import jakarta.persistence.*;

@Entity
public class BookOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer size;
    private Integer value;

    @OneToOne
    @JoinColumn(name = "bookId")
    private Book book;
    @OneToOne
    @JoinColumn(name = "orderId")
    private Order order;
}
