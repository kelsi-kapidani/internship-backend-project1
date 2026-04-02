package com.gisdev.library.service.iservice;

import com.gisdev.library.dto.request.user.UserCUDTO;
import com.gisdev.library.entity.LibraryUser;

import java.util.Optional;

public interface ILibraryUserService {

    Optional<LibraryUser> getUserById(Long id);
    
    boolean usernameExists(String username);

    Long createUser(UserCUDTO request);

    Object getUser(Long id);

    Long updateUser(Long id, UserCUDTO request);

    Long setUserActive(Long id);

    Long changePassword(Long id, String newPassword);
}
