package com.gisdev.library.dto.response.bookorder;

import com.gisdev.library.dto.response.book.ShortBookDTO;

public record BookOrderDTO(

        ShortBookDTO book,
        Integer size,
        Integer value
) {}
