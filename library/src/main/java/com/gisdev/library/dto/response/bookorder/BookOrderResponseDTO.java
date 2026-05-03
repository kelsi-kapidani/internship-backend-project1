package com.gisdev.library.dto.response.bookorder;

import com.gisdev.library.dto.response.book.BaseBookResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookOrderResponseDTO {

    private BaseBookResponseDTO book;
    private Integer size;
    private Integer value;
}
