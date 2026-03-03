package com.gisdev.library.dto.response.library;

import io.swagger.v3.oas.annotations.media.Schema;

public record ShortLibraryDTO(

        @Schema(example = "1")
        Long id,
        @Schema(example = "Libraria Kombetare Tirane")
        String name,
        @Schema(example = "Lagjia 3, Rruga Skenderbej")
        String address
) {}
