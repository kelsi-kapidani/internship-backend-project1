package com.gisdev.library.service;

import com.gisdev.library.constants.enums.Role;
import com.gisdev.library.dto.request.user.BaseUserRequestDTO;
import com.gisdev.library.dto.response.user.FullUserResponseDTO;
import com.gisdev.library.entity.Library;
import com.gisdev.library.entity.LibraryUser;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.repository.LibraryUserRepository;
import com.gisdev.library.service.iservice.ILibraryService;
import com.gisdev.library.service.iservice.ILibraryUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryUserService implements ILibraryUserService {

    private final LibraryUserRepository userRepository;
    private final ILibraryService libraryService;
    private final ModelMapper modelMapper;

    @Override
    public void usernameExists(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new BadRequestException("Username already exists");
        }
    }

    @Override
    public LibraryUser getUserById(Long id, String exceptionMessage) {
        return userRepository.findById(id).orElseThrow(() -> new BadRequestException(exceptionMessage));
    }

    @Override
    public Long createUser(BaseUserRequestDTO request) {
        usernameExists(request.getUsername());

        Library library = libraryService.getLibraryById(request.getLibrary_id(),"Library of the user does not exist");
        LibraryUser user = modelMapper.map(request, LibraryUser.class);

        user.setLibrary(library);
        user.setRole(Role.USER);
        userRepository.save(user);

        return user.getId();
    }

    @Override
    public FullUserResponseDTO getUser(Long id) {

        LibraryUser user = getUserById(id, "User with this id does not exist");
        return modelMapper.map(user, FullUserResponseDTO.class);
    }

    @Override
    public Long updateUser(Long id, BaseUserRequestDTO request) {
        LibraryUser user = getUserById(id,"User you are trying to update does not exist");
        Library library = libraryService.getLibraryById(request.getLibrary_id(),"Library of the user does not exist");

        modelMapper.map(request,user);
        user.setLibrary(library);
        userRepository.save(user);

        return id;
    }

    @Override
    public Long setUserActive (Long id) {
        LibraryUser user = getUserById(id,"User with this id does not exist");

        user.setActive(true);
        userRepository.save(user);

        return id;
    }

    @Override
    public Long changePassword (Long id, String newPassword) {
        LibraryUser user = getUserById(id,"User with this id does not exist");

        if (user.getPassword().equals(newPassword)) {
            throw new BadRequestException("This password is the old one");
        }

        user.setPassword(newPassword);
        userRepository.save(user);

        return id;
    }
}
