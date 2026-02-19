package com.gisdev.library.dto.response;

import com.gisdev.library.entity.Library;

import java.util.List;

public record OrderDTO(

        Long id,
        Integer total,
        LibraryDTO.LibraryUsersDTO user,
        List<OrderBookDTO> books
) {
    public record OrderBookDTO(

            LibraryDTO.LibraryBooksDTO book,
            Integer size,
            Integer value

    ) {}
}
