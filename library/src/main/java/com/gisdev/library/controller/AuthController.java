package com.gisdev.library.controller;

import com.gisdev.library.dto.request.security.LoginDTO;
import com.gisdev.library.dto.response.security.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.gisdev.library.service.iservice.IAuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO request) {

        return authService.handleLogIn(request);
    }
}
