package com.gisdev.library.entity;

import com.gisdev.library.constants.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class LibraryOrder extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "userId")
    private LibraryUser user;
    private String note;

    @OneToMany(mappedBy = "order")
    private List<BookOrder> books;
}
