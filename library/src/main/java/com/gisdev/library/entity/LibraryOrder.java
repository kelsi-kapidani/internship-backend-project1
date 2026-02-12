package com.gisdev.library.entity;

import com.gisdev.library.constants.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class LibraryOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer OrderNumber;
    private Long userId;
    private String note;

    @OneToMany()
    @ToString.Exclude
    private List<BookOrder> books;
}
