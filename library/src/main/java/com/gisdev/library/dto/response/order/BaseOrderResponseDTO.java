package com.gisdev.library.dto.response.order;

import com.gisdev.library.constants.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseOrderResponseDTO {

    private Long id;
    private Status status;
}
