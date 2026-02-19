package com.gisdev.library.mapper;

import com.gisdev.library.dto.request.UserCreateDTO;
import com.gisdev.library.dto.request.UserUpdateDTO;
import com.gisdev.library.dto.response.UserDTO;
import com.gisdev.library.entity.LibraryUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(expression = "java(mapLibrary(user))", target = "library")
    UserDTO toDto(LibraryUser user);

    @Mapping(target = "active", constant = "false")
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "library", ignore = true)
    LibraryUser toEntity(UserCreateDTO dto);

    @Mapping(target = "library", ignore = true)
    void updateUserFromDto(UserUpdateDTO dto, @MappingTarget LibraryUser entity);

    default UserDTO.UserLibraryDTO mapLibrary (LibraryUser user) {
        var lib = user.getLibrary();
        return new UserDTO.UserLibraryDTO(lib.getId(), lib.getName(), lib.getAddress());
    }
}
