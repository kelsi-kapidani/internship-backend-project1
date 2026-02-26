package com.gisdev.library.dto.response.order;

import com.gisdev.library.constants.enums.Status;

public record ShortOrderDTO(

        Long id,
        Status status
) {}
