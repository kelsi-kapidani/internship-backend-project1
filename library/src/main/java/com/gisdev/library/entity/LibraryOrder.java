package com.gisdev.library.entity;

import com.gisdev.library.constants.enums.Status;
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
public class LibraryOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "userId")
    private LibraryUser user;
    private String note;

    @OneToMany(mappedBy = "order")
    @ToString.Exclude
    private List<BookOrder> books;
}
