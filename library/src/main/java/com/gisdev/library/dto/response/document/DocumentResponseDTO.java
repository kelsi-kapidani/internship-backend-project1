package com.gisdev.library.dto.response.document;

import com.gisdev.library.constants.enums.Genre;
import com.gisdev.library.constants.enums.Section;
import com.gisdev.library.constants.enums.Source;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponseDTO {

    private Long id;
    private String name;
    private Source source;
    private Long sourceId;
    private Long size;
    private LocalDateTime uploadTime;
}
