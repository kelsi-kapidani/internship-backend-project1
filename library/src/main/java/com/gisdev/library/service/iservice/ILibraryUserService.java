package com.gisdev.library.service.iservice;

import com.gisdev.library.dto.request.user.BaseUserRequestDTO;
import com.gisdev.library.dto.request.user.ChangeUserRequestDTO;
import com.gisdev.library.dto.request.user.NewPasswordRequestDTO;
import com.gisdev.library.dto.response.user.FullUserResponseDTO;
import com.gisdev.library.entity.LibraryUser;

public interface ILibraryUserService {

    LibraryUser getUserById(Long id, String exceptionMessage);
    
    void usernameExists(String username);

    Long createUser(BaseUserRequestDTO request);

    FullUserResponseDTO getUser(Long id);

    Long updateUser(Long id, ChangeUserRequestDTO request);

    Long setUserActive(Long id);

    Long changePassword(Long id, NewPasswordRequestDTO request);
}
