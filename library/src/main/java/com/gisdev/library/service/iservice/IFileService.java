package com.gisdev.library.service.iservice;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    public String save(MultipartFile file);
    public Resource load(String location);
    public void delete(String location);
}
