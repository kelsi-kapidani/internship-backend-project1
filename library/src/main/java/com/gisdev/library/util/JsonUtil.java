package com.gisdev.library.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class JsonUtil {

    @Getter
    private final static ModelMapper mapper = new ModelMapper();

    private JsonUtil() {
    }

    public static <T> T map(Object payload, Class<T> destinationClass) {
        return getMapper().map(payload, destinationClass);
    }
}
