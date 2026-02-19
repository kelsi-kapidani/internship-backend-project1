package com.gisdev.library.dto.request;

import com.gisdev.library.constants.enums.Status;
import com.gisdev.library.entity.Book;
import com.gisdev.library.entity.BookOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record OrderCreateDTO(

        @NotEmpty List<BookOrderRequest> books
) {
    public record BookOrderRequest (

        Long bookId,
        Integer amount
    ){}
}
