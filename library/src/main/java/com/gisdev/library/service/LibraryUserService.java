package com.gisdev.library.service;

import com.gisdev.library.constants.enums.Role;
import com.gisdev.library.dto.request.CreateUserRequest;
import com.gisdev.library.entity.LibraryUser;
import com.gisdev.library.repository.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryUserService {

    public final LibraryUserRepository userRepository;

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean idExists(Long id) {
        return userRepository.existsById(id);
    }

    public LibraryUser createUser(CreateUserRequest request) {

        LibraryUser user = LibraryUser.builder()
                .name(request.name())
                .surname(request.surname())
                .username(request.username())
                .password(request.password())
                .email(request.email())
                .active(false)
                .role(Role.USER)
                .libraryId(request.libraryId())
                .build();

        return userRepository.save(user);
    }

    public LibraryUser getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
