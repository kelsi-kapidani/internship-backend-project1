package com.gisdev.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseError {

    private String message;

    public ResponseError(String message) {
        this.message = message;
    }
}
