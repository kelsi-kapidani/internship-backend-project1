package com.gisdev.library.service.iservice;

import com.gisdev.library.dto.response.document.DocumentDownloadResponseDTO;
import com.gisdev.library.dto.response.document.DocumentResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDocumentService {

    Long uploadDocument(MultipartFile file, String requestJson);

    DocumentDownloadResponseDTO downloadDocument(Long id);

    List<DocumentResponseDTO> getAllDocuments(List<String> filter, String sort);

    Long deleteDocument(Long id);

}
