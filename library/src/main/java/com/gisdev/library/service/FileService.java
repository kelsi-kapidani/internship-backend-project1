package com.gisdev.library.service;

import com.gisdev.library.exception.BadRequestException;
import com.gisdev.library.service.iservice.IFileService;
import com.gisdev.library.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileService implements IFileService {

    private final FileUtil fileUtil;

    @Value("${document.storage.path}")
    private String storagePath;


    @Override
    public String save(MultipartFile file) {
        try {
            String storedName = fileUtil.generateStoredFileName(file.getOriginalFilename());
            Path path = Paths.get(storagePath, storedName);

            Files.copy(
                file.getInputStream(),
                path,
                StandardCopyOption.REPLACE_EXISTING
            );

            return path.toString();
        } catch (IOException e) {
            throw new BadRequestException("Failed to save uploaded file");
        }
    }

    @Override
    public Resource load(String location) {
        try {
            return new UrlResource(Paths.get(location).toUri());
        } catch (MalformedURLException e) {
            throw new BadRequestException("Invalid stored file location");
        }
    }

    @Override
    public void delete(String location) {
        try {
            Files.deleteIfExists(Paths.get(location));
        } catch (IOException e) {
            throw new BadRequestException("Document file does not exist");
        }
    }
}
