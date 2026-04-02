package com.gisdev.library.dto.response.library;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseLibraryDTO {

    private Long id;
    private String name;
    private String address;

}
