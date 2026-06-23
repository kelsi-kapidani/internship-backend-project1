package com.gisdev.library.controller;

import com.gisdev.library.constants.enums.Source;
import com.gisdev.library.dto.request.book.BaseBookRequestDTO;
import com.gisdev.library.dto.request.book.DownloadRequestDTO;
import com.gisdev.library.service.iservice.IDocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    Long response = 1L;
    private final IDocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<Long> uploadDocument(@RequestParam MultipartFile file, @RequestParam Source source, @RequestParam Long sourceId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/download")
    public ResponseEntity<Long> downloadDocument(@Valid @RequestBody DownloadRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Long> getAllDocuments(@RequestParam(required = false) List<String> filter, @RequestParam(required = false) String sort) {

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteDocument(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
