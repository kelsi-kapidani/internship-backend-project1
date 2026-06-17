package com.gisdev.library.service;

import com.gisdev.library.constants.enums.Role;
import com.gisdev.library.dto.request.user.BaseUserRequestDTO;
import com.gisdev.library.dto.request.user.ChangeUserRequestDTO;
import com.gisdev.library.dto.request.user.NewPasswordRequestDTO;
import com.gisdev.library.dto.response.user.FullUserResponseDTO;
import com.gisdev.library.entity.Library;
import com.gisdev.library.entity.LibraryUser;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.repository.LibraryUserRepository;
import com.gisdev.library.service.iservice.ILibraryService;
import com.gisdev.library.service.iservice.ILibraryUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryUserService implements ILibraryUserService {

    private final LibraryUserRepository userRepository;
    private final ILibraryService libraryService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

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
    public LibraryUser getUserByUsername(String username, String exceptionMessage) {
        return(userRepository.findByUsername(username).orElseThrow(() -> new BadRequestException(exceptionMessage)));
    }

    @Override
    public Long createUser(BaseUserRequestDTO request) {
        usernameExists(request.getUsername());

        Library library = libraryService.getLibraryById(request.getLibrary_id(),"Library of the user does not exist");
        request.setPassword(passwordEncoder.encode(request.getPassword()));
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
    public Long updateUser(Long id, ChangeUserRequestDTO request) {
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
    public Long changePassword (Long id, NewPasswordRequestDTO request) {
        LibraryUser user = getUserById(id,"User with this id does not exist");
        if(!request.getNewPassword().equals(request.getRepeatedPassword())) {
            throw new BadRequestException("Repeated password does not match the new password");
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);

        return id;
    }
}
