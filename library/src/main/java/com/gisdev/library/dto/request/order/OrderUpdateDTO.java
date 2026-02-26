package com.gisdev.library.dto.request.order;

import com.gisdev.library.constants.enums.Status;
import jakarta.validation.constraints.NotNull;

public record OrderUpdateDTO(

        String note,
        @NotNull(message = "New status needs to be defined")
        Status status
) {}
