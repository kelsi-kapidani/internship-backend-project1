package com.gisdev.library.dto.response.book;

import com.gisdev.library.dto.response.library.BaseLibraryDTO;
import com.gisdev.library.dto.response.order.BaseOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FullBookDTO extends BaseBookDTO{

    private List<BaseOrderDTO> orders;
    private List<BaseLibraryDTO> libraries;
}
