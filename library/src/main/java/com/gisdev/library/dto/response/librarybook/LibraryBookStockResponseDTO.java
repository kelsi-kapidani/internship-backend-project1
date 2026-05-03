package com.gisdev.library.dto.response.librarybook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryBookStockResponseDTO {

    private Long libraryId;
    private String libraryName;
    private Integer stock;
}
