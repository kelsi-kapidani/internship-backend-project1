package com.gisdev.library.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record LibraryBookAddDTO(

        @NotNull(message = "List of books is empty")
        List<Long> idOfBooks
        ) {}
