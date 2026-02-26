package com.gisdev.library.mapper;

import com.gisdev.library.dto.request.user.UserCUDTO;
import com.gisdev.library.dto.response.library.ShortLibraryDTO;
import com.gisdev.library.dto.response.user.UserDTO;
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
    LibraryUser toEntity(UserCUDTO dto);

    @Mapping(target = "library", ignore = true)
    void updateUserFromDto(UserCUDTO dto, @MappingTarget LibraryUser entity);

    default ShortLibraryDTO mapLibrary (LibraryUser user) {
        var lib = user.getLibrary();
        return new ShortLibraryDTO(lib.getId(), lib.getName(), lib.getAddress());
    }
}
