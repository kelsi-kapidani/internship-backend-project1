package com.gisdev.library.mapper;
import com.gisdev.library.dto.request.book.BookCUDTO;
import com.gisdev.library.dto.response.book.BookDTO;
import com.gisdev.library.dto.response.bookorder.BookOrderDTO;
import com.gisdev.library.dto.response.library.ShortLibraryDTO;
import com.gisdev.library.dto.response.order.ShortOrderDTO;
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
    Book toEntity(BookCUDTO dto);

    default List<ShortLibraryDTO> mapLibraries(Book book) {

        return book.getLibraries().stream()
                .map(lb -> {
                    var lib = lb.getLibrary();
                    if (lib == null) return null;
                    return new ShortLibraryDTO(lib.getId(), lib.getName(), lib.getAddress());
                }).collect(Collectors.toList());
    }

    default List<ShortOrderDTO> mapOrders(Book book) {

        return book.getOrders().stream()
                .map(or -> {
                    var order = or.getOrder();
                    if (order == null) return null;
                    return new ShortOrderDTO(order.getId(), order.getStatus());
                }).collect(Collectors.toList());
    }

    void updateBookFromDto(BookCUDTO dto, @MappingTarget Book entity);
}
