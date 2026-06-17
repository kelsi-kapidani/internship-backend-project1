package com.gisdev.library.controller;

import com.gisdev.library.dto.request.security.LoginDTO;
import com.gisdev.library.dto.request.user.BaseUserRequestDTO;
import com.gisdev.library.dto.response.security.TokenDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gisdev.library.service.iservice.IAuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/login")
    public TokenDTO logIn(@RequestBody LoginDTO request) {

        return authService.handleLogIn(request);
    }
}
