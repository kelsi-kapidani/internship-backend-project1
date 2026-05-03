package com.gisdev.library.dto.response.library;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FullLibraryResponseDTO extends BaseLibraryResponseDTO{

    private List<ShortUserDTO> users;
    private List<ShortBookDTO> books;
}
