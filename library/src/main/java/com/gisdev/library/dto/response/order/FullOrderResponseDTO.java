package com.gisdev.library.dto.response.order;

import com.gisdev.library.dto.response.bookorder.BookOrderResponseDTO;
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
public class FullOrderResponseDTO {

    private Long id;
    private Integer total;
    private BaseUserResponseDTO user;
    private List<BookOrderResponseDTO> books;
}
