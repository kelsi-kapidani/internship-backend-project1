package com.gisdev.library.service;

import com.gisdev.library.constants.enums.Role;
import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.request.UserCreateDTO;
import com.gisdev.library.dto.request.UserUpdateDTO;
import com.gisdev.library.entity.LibraryUser;
import com.gisdev.library.exception.BadRequestException;
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

    public Object createUser(UserCreateDTO request) {

        if (usernameExists(request.username())) {
            return new BadRequestException("Username already exists");
        }
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

    public Object getUser(Long id) {

        LibraryUser user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new BadRequestException("User with this id does not exist");
        }
        return user;
    }

    public Object updateUser(Long id, UserUpdateDTO request) {

        LibraryUser user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new BadRequestException("User you are trying to update does not exist");
        }
        user.setName(request.name());
        user.setSurname(request.surname());
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setEmail(request.email());
        user.setLibraryId(request.libraryId());

        return userRepository.save(user);
    }

    public Object setUserActive (Long id) {

        LibraryUser user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return new BadRequestException("User with this id does not exist");
        }
        user.setActive(true);
        return userRepository.save(user);
    }

    public Object changePassword (Long id, String newpass) {

        LibraryUser user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new BadRequestException("User with this id does not exist");
        }
        if (user.getPassword() == newpass) {
            return new BadRequestException("This password is the old one");
        }
        user.setPassword(newpass);
        return userRepository.save(user);
    }
}
