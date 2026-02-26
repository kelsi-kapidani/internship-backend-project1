package com.gisdev.library.entity;

import com.gisdev.library.constants.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class LibraryUser extends BaseEntity{

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private boolean active;

    @ManyToOne()
    @JoinColumn(name = "library_id")
    private Library library;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<LibraryOrder> orders;

}
