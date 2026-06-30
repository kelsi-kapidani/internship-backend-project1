package com.gisdev.library.dto.request.document;

import com.gisdev.library.constants.enums.Source;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadRequestDTO {

    @NotBlank(message = "Source id required")
    private Long sourceId;
    @NotBlank(message = "Source type required")
    private Source source;
}
