package com.gisdev.library.dto.request.order;

import com.gisdev.library.constants.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateRequestDTO {

    private String note;
    @NotNull(message = "New status needs to be defined")
    private Status status;
}
