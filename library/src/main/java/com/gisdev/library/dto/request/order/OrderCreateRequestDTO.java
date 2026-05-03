package com.gisdev.library.dto.request.order;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequestDTO {

    @NotEmpty(message = "List of books should not be empty")
    private List<BookOrderRequestDTO> books;
}
