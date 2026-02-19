package com.gisdev.library.dto.request;

import com.gisdev.library.constants.enums.Status;
import jakarta.validation.constraints.NotNull;

public record OrderUpdateDTO(

        String note,
        @NotNull Status status
) {}
