package com.gisdev.library.service;

import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.request.user.UserCUDTO;
import com.gisdev.library.entity.Library;
import com.gisdev.library.entity.LibraryUser;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.mapper.UserMapper;
import com.gisdev.library.repository.LibraryRepository;
import com.gisdev.library.repository.LibraryUserRepository;
import com.gisdev.library.service.iservice.ILibraryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryUserService implements ILibraryUserService {

    public final LibraryUserRepository userRepository;
    public final LibraryRepository libraryRepository;
    public final UserMapper userMapper;

    @Override
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Long createUser(UserCUDTO request) {

        if (usernameExists(request.username())) {
            throw new BadRequestException("Username already exists");
        }
        Library library = libraryRepository.findById(request.library_id()).orElse(null);
        if (library == null) {
            throw new BadRequestException("Library of the user does not exist");
        }
        LibraryUser user = userMapper.toEntity(request);
        user.setLibrary(library);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public Object getUser(Long id) {

        LibraryUser user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new BadRequestException("User with this id does not exist");
        }
        return userMapper.toDto(user);
    }

    @Override
    public Long updateUser(Long id, UserCUDTO request) {

        LibraryUser user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new BadRequestException("User you are trying to update does not exist");
        }
        Library library = libraryRepository.findById(request.library_id()).orElse(null);
        if (library == null) {
            throw new BadRequestException("Library of the user does not exist");
        }
        userMapper.updateUserFromDto(request, user);
        user.setLibrary(library);
        userRepository.save(user);
        return id;
    }

    @Override
    public Long setUserActive (Long id) {

        LibraryUser user = userRepository.findById(id).orElse(null);
        if(user == null) {
            throw new BadRequestException("User with this id does not exist");
        }
        user.setActive(true);
        userRepository.save(user);
        return id;
    }

    @Override
    public Long changePassword (Long id, String newpass) {

        LibraryUser user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new BadRequestException("User with this id does not exist");
        }
        if (user.getPassword().equals(newpass)) {
            throw new BadRequestException("This password is the old one");
        }
        user.setPassword(newpass);
        userRepository.save(user);
        return id;
    }
}
