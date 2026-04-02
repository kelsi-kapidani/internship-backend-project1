package com.gisdev.library.mapper;

import com.gisdev.library.dto.request.book.BookCUDTO;
import com.gisdev.library.dto.response.book.FullBookDTO;
import com.gisdev.library.entity.Book;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
