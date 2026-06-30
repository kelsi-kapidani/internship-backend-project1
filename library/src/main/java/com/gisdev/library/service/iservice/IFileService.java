package com.gisdev.library.service.iservice;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    String save(MultipartFile file);

    Resource load(String location);

    void delete(String location);
}
