package com.gisdev.library.service.iservice;

import com.gisdev.library.dto.request.security.LoginDTO;
import com.gisdev.library.dto.request.user.BaseUserRequestDTO;
import com.gisdev.library.dto.response.security.TokenDTO;
import com.gisdev.library.entity.LibraryUser;

public interface IAuthService {

    LibraryUser getUserByUsername(String username, String exceptionMessage);

    LibraryUser getUserByToken();

    TokenDTO handleLogIn(LoginDTO request);

}
