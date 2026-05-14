package com.gisdev.library.service;

import com.gisdev.library.dto.request.library.BaseLibraryRequestDTO;
import com.gisdev.library.dto.response.library.FullLibraryResponseDTO;
import com.gisdev.library.entity.Library;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.repository.LibraryRepository;
import com.gisdev.library.service.iservice.ILibraryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService implements ILibraryService {

    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;

    @Override
    public void nameExists(String name) {
        if (libraryRepository.existsByName(name)) {
            throw new BadRequestException("Library with this name already exists");
        }
    }

    @Override
    public void idExists(Long id) {
        if (libraryRepository.existsById(id)) {
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
    public List<FullLibraryResponseDTO> getAllLibraries(String name, String address) {

        List<FullLibraryResponseDTO> response = new ArrayList<>();

        for (Library library: libraryRepository.findAllWithFilters(name, address)) {
            response.add(modelMapper.map(library, FullLibraryResponseDTO.class));
        }

        return response;
    }

}
