package com.gisdev.library.service;

import com.gisdev.library.constants.enums.Source;
import com.gisdev.library.entity.Document;
import com.gisdev.library.repository.DocumentRepository;
import com.gisdev.library.service.iservice.IFileService;
import com.gisdev.library.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.gisdev.library.service.iservice.IDocumentService;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DocumentService implements IDocumentService{

    private final DocumentRepository documentRepository;
    private final IFileService fileService;

    //@Override
    public Long uploadDocument(MultipartFile file, Source source, Long sourceId) {

        String location = fileService.save(file);

        Document document = new Document();

        document.setName(Paths
                .get(location)
                .getFileName()
                .toString()
        );

        document.setLocation(location);
        document.setSource(source);
        document.setSourceId(sourceId);
        document.setUploadTime(LocalDateTime.now());
        document.setSize(file.getSize());

        documentRepository.save(document);

        return document.getId();
    }


    @Override
    public Resource download(Long id) {

        Document document =
                documentRepository.findById(id)
                        .orElseThrow();

        return fileService.load(
                document.getLocation()
        );
    }

    @Override
    public Page<Document> search(
            String search,
            Pageable pageable
    ) {

        Specification<Document> spec =
                (root, query, cb) -> {

                    if(search == null || search.isBlank()) {
                        return cb.conjunction();
                    }

                    return cb.like(
                            cb.lower(root.get("name")),
                            "%" + search.toLowerCase() + "%"
                    );
                };

        return documentRepository.findAll(
                spec,
                pageable
        );
    }


}
