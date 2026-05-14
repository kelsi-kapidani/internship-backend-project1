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

    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private LibraryUser user;

    @OneToMany(mappedBy = "order", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<BookLibraryOrder> books;
}
