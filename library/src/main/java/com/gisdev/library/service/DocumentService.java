package com.gisdev.library.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gisdev.library.constants.enums.Source;
import com.gisdev.library.dto.request.document.UploadRequestDTO;
import com.gisdev.library.dto.response.document.DocumentDownloadResponseDTO;
import com.gisdev.library.dto.response.document.DocumentResponseDTO;
import com.gisdev.library.entity.Document;
import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.repository.DocumentRepository;
import com.gisdev.library.service.iservice.IFileService;
import com.gisdev.library.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.gisdev.library.service.iservice.IDocumentService;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DocumentService implements IDocumentService{

    private final DocumentRepository documentRepository;
    private final IFileService fileService;
    private final FileUtil fileUtil;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;

    @Override
    public Long uploadDocument(MultipartFile file, String requestJson) {
        UploadRequestDTO request;
        try {
            request = objectMapper.readValue(requestJson, UploadRequestDTO.class);
        } catch (Exception e) {
            throw new BadRequestException("Invalid request JSON");
        }

        String location = fileService.save(file);
        Document document = new Document();
        document.setName(Paths
                .get(location)
                .getFileName()
                .toString()
        );

        document.setLocation(location);
        document.setSource(request.getSource());
        document.setSourceId(request.getSourceId());
        document.setUploadTime(LocalDateTime.now());
        document.setSize(file.getSize());
        documentRepository.save(document);

        return document.getId();
    }

    @Override
    public DocumentDownloadResponseDTO downloadDocument(Long id) {
        Document document = documentRepository.findById(id).orElseThrow();
        fileService.load(document.getLocation());
        return new DocumentDownloadResponseDTO(fileService.load(document.getLocation()), fileUtil.removeTimestampPrefix(document.getName()));
    }

    @Override
    public List<DocumentResponseDTO> getAllDocuments(List<String> filter, String sort) {

        List<DocumentResponseDTO> response = new ArrayList<>();

        for (Document document : documentRepository.findAll(genSpecs(filter), genSort(sort))) {
            DocumentResponseDTO dto = mapper.map(document, DocumentResponseDTO.class);
            dto.setName(fileUtil.removeTimestampPrefix(dto.getName()));
            response.add(dto);
        }

        return response;
    }

    @Override
    public Long deleteDocument(Long id) {

        Document document = documentRepository.findById(id).orElseThrow();
        fileService.delete(document.getLocation());
        documentRepository.delete(document);
        return id;
    }

    public Specification<Document> genSpecs(List<String> filters) {

        if (filters == null || filters.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }
        if (filters.getFirst().isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String[] filtersArray = filters.getFirst().split(",");

        Specification<Document> specs = (root, query, cb) -> cb.conjunction();

        for (String filter : filtersArray) {

            String[] parts = filter.split(":");
            if (!allowedFields.contains(parts[0])) {
                throw new BadRequestException("The filtering field " + parts[0] + " is not legal");
            }
            switch (parts[1]) {
                case "eq":
                    specs = specs.and((root, query, cb) -> cb.equal(root.get(parts[0]), parts[2]));
                    break;
                case "neq":
                    specs = specs.and((root, query, cb) -> cb.notEqual(root.get(parts[0]), parts[2]));
                    break;
                case "gt":
                    specs = specs.and((root, query, cb) -> cb.greaterThan(root.get(parts[0]), parts[2]));
                    break;
                case "geq":
                    specs = specs.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get(parts[0]), parts[2]));
                    break;
                case "lt":
                    specs = specs.and((root, query, cb) -> cb.lessThan(root.get(parts[0]), parts[2]));
                    break;
                case "leq":
                    specs = specs.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get(parts[0]), parts[2]));
                    break;
                case "ilike":
                    specs = specs.and((root, query, cb) -> cb.like(cb.lower(root.get(parts[0])),"%" + parts[2].toLowerCase() + "%"));
                    break;
                default:
                    throw new BadRequestException("The operator " + parts[1] + " is not legal");
            }
        }
        return specs;
    }

    public Sort genSort(String sort) {

        if (sort == null || sort.isEmpty()) {
            return Sort.by("name").descending();
        }

        String[] parts = sort.split(":");

        if (parts.length < 2) {
            throw new BadRequestException("Sort format must be field:direction");
        }

        if (!allowedFields.contains(parts[0])) {
            throw new BadRequestException("Sorting field is not legal");
        }

        if (parts[1].equalsIgnoreCase("asc")) {
            return Sort.by(parts[0]).ascending();
        }

        return Sort.by(parts[0]).descending();
    }

    private static final Set<String> allowedFields = Set.of(
            "name",
            "size",
            "uploadTime",
            "source",
            "sourceId"
    );
}
