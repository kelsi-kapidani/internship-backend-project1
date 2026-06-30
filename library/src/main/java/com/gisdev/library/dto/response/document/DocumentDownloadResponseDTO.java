package com.gisdev.library.dto.response.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDownloadResponseDTO {

    private Resource resource;
    private String name;
}
