package com.gisdev.library.dto.response.order;

import com.gisdev.library.constants.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;

public record ShortOrderDTO(

        @Schema(example = "2")
        Long id,
        @Schema(example = "PENDING")
        Status status
) {}
