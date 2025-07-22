package com.example.HR.service.implement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
public class FileUploadService {

    public static void uploadFile(String uploadDir,
                           String filename,
                           MultipartFile uploadedFile) throws IOException {

        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try {
            InputStream inputStream = uploadedFile.getInputStream();
            Path filePath = uploadPath.resolve(filename);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new IOException(e.getMessage());
        }

    }


}
