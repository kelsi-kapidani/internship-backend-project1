package com.gisdev.library.mapper;

import com.gisdev.library.dto.request.library.LibraryCUDTO;
import com.gisdev.library.dto.response.book.ShortBookDTO;
import com.gisdev.library.dto.response.library.LibraryDTO;
import com.gisdev.library.dto.response.user.ShortUserDTO;
import com.gisdev.library.entity.Book;
import com.gisdev.library.entity.Library;
import com.gisdev.library.entity.LibraryUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LibraryMapper {

    @Mapping(expression = "java(mapUsers(library))", target = "users")
    @Mapping(expression = "java(mapBooks(library))", target = "books")
    LibraryDTO toDto(Library library);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "books", ignore = true)
    Library toEntity(LibraryCUDTO dto);


    ShortBookDTO toBookDto(Book book);

    ShortUserDTO toUserDto(LibraryUser user);

    default List<ShortUserDTO> mapUsers(Library library) {

        return library.getUsers().stream()
                .map(u -> {
                    if (u == null) return null;
                    return toUserDto(u);
                }).collect(Collectors.toList());
    }

    default List<ShortBookDTO> mapBooks(Library library) {

        return library.getBooks().stream()
                .map(b -> {
                    var book = b.getBook();
                    if (book == null) return null;
                    return toBookDto(book);
                }).collect(Collectors.toList());
    }

    void updateLibraryFromDto(LibraryCUDTO dto, @MappingTarget Library entity);
}
