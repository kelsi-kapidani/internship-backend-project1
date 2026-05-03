package com.gisdev.library.dto.response.library;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseLibraryResponseDTO {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "Libraria Kombetare Tirane")
    private String name;
    @Schema(example = "Lagjia 3, Rruga Skenderbej")
    private String address;

}
