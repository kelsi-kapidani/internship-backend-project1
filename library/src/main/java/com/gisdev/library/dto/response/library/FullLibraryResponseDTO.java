package com.gisdev.library.dto.response.library;

import com.gisdev.library.dto.response.book.BaseBookResponseDTO;
import com.gisdev.library.dto.response.user.BaseUserResponseDTO;
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

    private List<BaseUserResponseDTO> users;
    private List<BaseBookResponseDTO> books;
}
