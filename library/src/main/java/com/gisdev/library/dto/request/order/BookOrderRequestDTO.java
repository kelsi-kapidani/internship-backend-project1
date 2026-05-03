package com.gisdev.library.dto.request.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookOrderRequestDTO {

    @NotNull(message = "Id of book in the list not given")
    private Long bookId;
    @NotNull(message = "No given amount for book in the list")
    @Positive(message = "Not appropriate amount number given for book in the list")
    private Integer amount;
}
