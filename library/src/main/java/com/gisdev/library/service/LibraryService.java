package com.gisdev.library.service;

import com.gisdev.library.dto.request.library.BaseLibraryRequestDTO;
import com.gisdev.library.dto.response.library.BaseLibraryResponseDTO;
import com.gisdev.library.dto.response.library.FullLibraryResponseDTO;
import com.gisdev.library.entity.Library;
import com.gisdev.library.entity.LibraryUser;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.repository.LibraryRepository;
import com.gisdev.library.repository.LibraryUserRepository;
import com.gisdev.library.service.iservice.IAuthService;
import com.gisdev.library.service.iservice.ILibraryService;
import com.gisdev.library.service.iservice.ILibraryUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService implements ILibraryService {

    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;
    private final IAuthService authService;

    @Override
    public void nameExists(String name) {
        if (libraryRepository.existsByName(name)) {
            throw new BadRequestException("Library with this name already exists");
        }
    }

    @Override
    public void idExists(Long id) {
        if (!libraryRepository.existsById(id)) {
            throw new BadRequestException("Library with this id does not exist");
        }
    }

    @Override
    public Library getLibraryById(Long id, String exceptionMessage) {
        return libraryRepository.findById(id).orElseThrow(() -> new BadRequestException(exceptionMessage));
    }

    @Override
    public Long createLibrary(BaseLibraryRequestDTO request) {
        nameExists(request.getName());

        Library library = modelMapper.map(request, Library.class);
        libraryRepository.save(library);

        return library.getId();
    }

    @Override
    public Long updateLibrary(Long id, BaseLibraryRequestDTO request) {
        Library library = getLibraryById(id,"Library with this id does not exist");
        if(!request.getName().equals(library.getName())) {
            nameExists(request.getName());
        }

        modelMapper.map(request, library);
        libraryRepository.save(library);

        return id;
    }

    @Override
    public Long deleteLibrary(Long id) {

        idExists(id);
        libraryRepository.deleteById(id);
        return id;
    }

    @Override
    public List<BaseLibraryResponseDTO> getAllLibraries(String name, String address) {

        LibraryUser currentUser = authService.getUserByToken();
        List<BaseLibraryResponseDTO> response = new ArrayList<>();

        if (currentUser.getRole().name().equals("ADMIN")) {
            for (Library library : libraryRepository.findAllWithFilters(name, address)) {
                response.add(modelMapper.map(library, BaseLibraryResponseDTO.class));
            }
        } else {
            response.add(modelMapper.map(currentUser.getLibrary(),BaseLibraryResponseDTO.class));
        }

        return response;
    }

}
