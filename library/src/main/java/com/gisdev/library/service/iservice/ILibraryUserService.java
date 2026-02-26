package com.gisdev.library.service.iservice;

import com.gisdev.library.dto.request.user.UserCUDTO;

public interface ILibraryUserService {

    boolean usernameExists(String username);

    Long createUser(UserCUDTO request);

    Object getUser(Long id);

    Long updateUser(Long id, UserCUDTO request);

    Long setUserActive(Long id);

    Long changePassword(Long id, String newPassword);
}
