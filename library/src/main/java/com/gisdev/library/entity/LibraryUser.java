package com.gisdev.library.entity;

import com.gisdev.library.constants.enums.Role;
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
public class LibraryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;
    private boolean active;
    private Long libraryId;

    @OneToMany
    @ToString.Exclude
    private List<LibraryOrder> orders;

}
