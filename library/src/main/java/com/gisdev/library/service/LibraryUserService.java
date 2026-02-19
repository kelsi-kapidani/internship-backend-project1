package com.gisdev.library.service;

import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.request.UserCreateDTO;
import com.gisdev.library.dto.request.UserUpdateDTO;
import com.gisdev.library.entity.Library;
import com.gisdev.library.entity.LibraryUser;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.mapper.UserMapper;
import com.gisdev.library.repository.LibraryRepository;
import com.gisdev.library.repository.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryUserService {

    public final LibraryUserRepository userRepository;
    public final LibraryRepository libraryRepository;
    public final UserMapper userMapper;

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public Object createUser(UserCreateDTO request) {

        if (usernameExists(request.username())) {
            return new BadRequestException("Username already exists");
        }
        Library library = libraryRepository.findById(request.libraryId()).orElse(null);
        if (library == null) {
            return new BadRequestException("Library of the user does not exist");
        }
        LibraryUser user = userMapper.toEntity(request);
        user.setLibrary(library);
        userRepository.save(user);
        return new ResponseError("User created successfully");
    }

    public Object getUser(Long id) {

        LibraryUser user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new BadRequestException("User with this id does not exist");
        }
        return userMapper.toDto(user);
    }

    public Object updateUser(Long id, UserUpdateDTO request) {

        LibraryUser user = userRepository.findById(id).orElse(null);
        Library library = libraryRepository.findById(request.libraryId()).orElse(null);
        if (user == null) {
            return new BadRequestException("User you are trying to update does not exist");
        }
        if (library == null) {
            return new BadRequestException("Library of the user does not exist");
        }
        userMapper.updateUserFromDto(request, user);
        user.setLibrary(library);
        userRepository.save(user);
        return new ResponseError("User updated successfully");
    }

    public Object setUserActive (Long id) {

        LibraryUser user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return new BadRequestException("User with this id does not exist");
        }
        user.setActive(true);
        userRepository.save(user);
        return  new ResponseError("User activated sucessfully");
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
        userRepository.save(user);
        return new ResponseError("Password changed successfully");
    }
}
