package com.gisdev.library.dto.response.book;

import com.gisdev.library.dto.response.ListOfIdsResponseDTO;
import com.gisdev.library.dto.response.library.BaseLibraryResponseDTO;
import com.gisdev.library.dto.response.order.BaseOrderResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FullBookResponseDTO extends BaseBookResponseDTO {

    private List<ListOfIdsResponseDTO> orders;
    private List<ListOfIdsResponseDTO> libraries;
}
