package com.gisdev.library.dto.response.user;

import com.gisdev.library.dto.response.library.BaseLibraryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FullUserResponseDTO extends BaseUserResponseDTO{

    private boolean active;
    private BaseLibraryResponseDTO library;
}
