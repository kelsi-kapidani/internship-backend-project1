package com.gisdev.library.service;

import com.gisdev.library.constants.enums.Role;
import com.gisdev.library.dto.request.CreateUserRequest;
import com.gisdev.library.dto.request.UpdateUserRequest;
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

    public LibraryUser getUser(Long id) {

        return userRepository.findById(id).orElse(null);
    }

    public LibraryUser updateUser(Long id, UpdateUserRequest request) {

        LibraryUser user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return user;
        }

        if (request.name() != null) {
            user.setName(request.name());
        }
        if (request.surname() != null) {
            user.setSurname(request.surname());
        }
        if (request.username() != null) {
            user.setUsername(request.username());
        }
        if (request.password() != null) {
            user.setPassword(request.password());
        }
        if (request.email() != null) {
            user.setEmail(request.email());
        }
        if (request.libraryId() != null) {
            user.setLibraryId(request.libraryId());
        }
        return userRepository.save(user);
    }

    public boolean setUserActive (Long id) {
        if(getUser(id) == null) {
            return false;
        }
        getUser(id).setActive(true);
        return true;
    }
}
