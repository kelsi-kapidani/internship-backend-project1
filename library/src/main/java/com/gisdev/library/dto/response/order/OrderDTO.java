package com.gisdev.library.dto.response.order;

import com.gisdev.library.dto.response.user.ShortUserDTO;
import com.gisdev.library.dto.response.bookorder.BookOrderDTO;

import java.util.List;

public record OrderDTO(

        Long id,
        Integer total,
        ShortUserDTO user,
        List<BookOrderDTO> books
) {}
