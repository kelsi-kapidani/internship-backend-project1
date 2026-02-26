package com.gisdev.library.dto.request.order;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderCreateDTO(

        @NotEmpty(message = "List of books should not be empty")
        List<BookOrderRequest> books
) {
    public record BookOrderRequest (

        Long bookId,
        Integer amount
    ){}
}
