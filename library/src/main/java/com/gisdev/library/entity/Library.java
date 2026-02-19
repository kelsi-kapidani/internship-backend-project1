package com.gisdev.library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @OneToMany(mappedBy = "library")
    @ToString.Exclude
    private List<LibraryUser> users;
    @OneToMany(mappedBy = "library")
    @ToString.Exclude
    private List<LibraryBook> books;
}
