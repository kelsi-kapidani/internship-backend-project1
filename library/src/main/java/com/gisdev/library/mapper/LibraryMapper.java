package com.gisdev.library.mapper;

import com.gisdev.library.dto.request.LibraryCreateDTO;
import com.gisdev.library.dto.request.LibraryUpdateDTO;
import com.gisdev.library.dto.response.LibraryDTO;
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
    Library toEntity(LibraryCreateDTO dto);


    LibraryDTO.LibraryBooksDTO toBookDto(Book book);

    LibraryDTO.LibraryUsersDTO toUserDto(LibraryUser user);

    default List<LibraryDTO.LibraryUsersDTO> mapUsers(Library library) {

        return library.getUsers().stream()
                .map(u -> {
                    if (u == null) return null;
                    return toUserDto(u);
                }).collect(Collectors.toList());
    }

    default List<LibraryDTO.LibraryBooksDTO> mapBooks(Library library) {

        return library.getBooks().stream()
                .map(b -> {
                    var book = b.getBook();
                    if (book == null) return null;
                    return toBookDto(book);
                }).collect(Collectors.toList());
    }

    void updateLibraryFromDto(LibraryUpdateDTO dto, @MappingTarget Library entity);
}
