package com.gisdev.library.dto.request;

import com.gisdev.library.entity.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AddBooksRequest(

        @NotNull(message = "List of books is empty")
        List<Long> idOfBooks
        ) {}
