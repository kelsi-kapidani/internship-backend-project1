package com.gisdev.library.service.iservice;

import com.gisdev.library.constants.enums.Source;
import com.gisdev.library.entity.Document;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface IDocumentService {
    public Long uploadDocument(MultipartFile file, Source source, Long sourceId);
    public Resource download(Long id);
    public Page<Document> search(
            String search,
            Pageable pageable
    );

}
