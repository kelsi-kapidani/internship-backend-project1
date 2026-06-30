package com.gisdev.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gisdev.library.constants.enums.Source;
import com.gisdev.library.dto.request.document.UploadRequestDTO;
import com.gisdev.library.dto.response.document.DocumentDownloadResponseDTO;
import com.gisdev.library.dto.response.document.DocumentResponseDTO;
import com.gisdev.library.service.iservice.IDocumentService;
import com.gisdev.library.service.iservice.IFileService;
import com.gisdev.library.util.FileUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    private final IDocumentService documentService;
    private final IFileService fileService;
    private final FileUtil fileUtil;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> uploadDocument(@RequestPart("file") MultipartFile file, @RequestPart("request")  String requestJson){

        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.uploadDocument(file, requestJson));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable long id) {
        DocumentDownloadResponseDTO response = documentService.downloadDocument(id);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + response.getName()+ "\"")
            .body(response.getResource());
    }

    @GetMapping("/all")
    public List<DocumentResponseDTO> getAllDocuments(@RequestParam(required = false) List<String> filter, @RequestParam(required = false) String sort) {

        return documentService.getAllDocuments(filter, sort);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteDocument(@PathVariable Long id) {

        return ResponseEntity.ok(documentService.deleteDocument(id));
    }

}
