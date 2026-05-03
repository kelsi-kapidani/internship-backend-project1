package com.gisdev.library.service.iservice;

import com.gisdev.library.dto.request.library.BaseLibraryRequestDTO;
import com.gisdev.library.dto.response.library.FullLibraryResponseDTO;
import com.gisdev.library.entity.Library;

import java.util.List;

public interface ILibraryService {

    void nameExists(String name);

    void idExists(Long id);

    Library getLibraryById(Long id, String exceptiponMessage);

    Long createLibrary(BaseLibraryRequestDTO request);

    Long updateLibrary(Long id, BaseLibraryRequestDTO request);

    Long deleteLibrary(Long id);

    List<FullLibraryResponseDTO> getAllLibraries(String name, String address);
}
