package com.gisdev.library.mapper;
import com.gisdev.library.dto.request.BookCreateDTO;
import com.gisdev.library.dto.request.BookUpdateDTO;
import com.gisdev.library.dto.response.BookDTO;
import com.gisdev.library.dto.response.LibraryDTO;
import com.gisdev.library.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(expression = "java(mapLibraries(book))", target = "libraries")
    @Mapping(expression = "java(mapOrders(book))", target = "orders")
    BookDTO toDto(Book book);

    @Mapping(target = "libraries", ignore = true)
    @Mapping(target = "orders", ignore = true)
    Book toEntity(BookCreateDTO dto);

    default List<BookDTO.BookLibrariesDTO> mapLibraries(Book book) {

        return book.getLibraries().stream()
                .map(lb -> {
                    var lib = lb.getLibrary();
                    if (lib == null) return null;
                    return new BookDTO.BookLibrariesDTO(lib.getId(), lib.getName(), lib.getAddress());
                }).collect(Collectors.toList());
    }

    default List<BookDTO.BookOrdersDTO> mapOrders(Book book) {

        return book.getOrders().stream()
                .map(or -> {
                    var order = or.getOrder();
                    if (order == null) return null;
                    return new BookDTO.BookOrdersDTO(order.getId(), order.getStatus());
                }).collect(Collectors.toList());
    }

    void updateBookFromDto(BookUpdateDTO dto, @MappingTarget Book entity);
}
