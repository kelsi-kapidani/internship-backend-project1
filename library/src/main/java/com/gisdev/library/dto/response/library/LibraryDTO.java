package com.gisdev.library.dto.response.library;

import com.gisdev.library.dto.response.user.ShortUserDTO;
import com.gisdev.library.dto.response.book.ShortBookDTO;

import java.util.List;

public record LibraryDTO(

        Long id,
        String name,
        String address,
        List<ShortUserDTO> users,
        List<ShortBookDTO> books
) {}
