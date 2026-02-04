package com.gisdev.library.entity;

import com.gisdev.library.constants.enums.Role;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    private String username;
    private String password;

    private Role role;
    private String email;
    private boolean active;
    private Long libraryId;

    @OneToMany
    private List<Order> orders;
}
