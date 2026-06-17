package com.gisdev.library.service;

import com.gisdev.library.constants.enums.Role;
import com.gisdev.library.dto.request.security.LoginDTO;
import com.gisdev.library.dto.request.user.BaseUserRequestDTO;
import com.gisdev.library.dto.response.security.TokenDTO;
import com.gisdev.library.entity.Library;
import com.gisdev.library.entity.LibraryUser;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.repository.LibraryUserRepository;
import com.gisdev.library.security.JwtService;
import com.gisdev.library.service.iservice.IAuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final LibraryUserRepository userRepository;

    @Override
    public LibraryUser getUserByUsername(String username, String exceptionMessage) {
        return(userRepository.findByUsername(username).orElseThrow(() -> new BadRequestException(exceptionMessage)));
    }

    @Override
    public LibraryUser getUserByToken() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        return getUserByUsername(username, "Request not from a current valid user");
    }

    @Override
    public TokenDTO handleLogIn(LoginDTO request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (DisabledException e) {
            throw new BadRequestException("Your account is not active");
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Invalid username or password");
        }

        String token = jwtService.generateToken(request.getUsername());

        return new TokenDTO(token);
    }

}
