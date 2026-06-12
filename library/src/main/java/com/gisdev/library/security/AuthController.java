package com.gisdev.library.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    //dto me vete per tu bere
    public record LoginRequest(String username, String password) {}
    public record AuthResponse(String token) {}

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
/*
        String freshHash = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("07042001Kk");
        System.out.println(">>> THE PERFECT HARD CODED JAVA HASH: " + freshHash);
*/
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(),request.password()));

        String token = jwtService.generateToken(request.username());

        return new AuthResponse(token);
    }
}
