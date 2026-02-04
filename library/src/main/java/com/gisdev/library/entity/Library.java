package com.gisdev.library.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @OneToMany
    private List<User> users;
    @OneToMany
    private List<LibraryBook> books;
}
