package com.example.HR.service.implement.fileStorage;

import com.example.HR.config.TaskFileStorageProperties;
import com.example.HR.entity.task.Task;
import com.example.HR.entity.task.TaskAttachment;
import com.example.HR.exception.FileStorageException;
import com.example.HR.repository.task.TaskAttachmentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskImageStorageService {
    private final TaskFileStorageProperties imagesStorageProperties;
    private final TaskAttachmentRepository attachmentRepository;

    private Path fileStorageLocation;

    @PostConstruct
    public void init(){
        this.fileStorageLocation = Paths.get(imagesStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }

    public TaskAttachment storeFile(MultipartFile file, Task task){
        validateFile(file);

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(originalFileName);
        String fileName = UUID.randomUUID().toString() + "." + fileExtension;

        try {
            if (originalFileName.contains("..")){
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + originalFileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(originalFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            TaskAttachment attachment = TaskAttachment.builder()
                    .fileName(fileName)
                    .originalFileName(originalFileName)
                    .filePath(targetLocation.toString())
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .task(task)
                    .build();

            return attachmentRepository.save(attachment);
        } catch (Exception e) {
            throw new FileStorageException("Could not store file " + originalFileName + ". Please try again!", e);
        }
    }

    public Resource loadFileAsResource(String originalFileName){
        try {
            Path filePath = this.fileStorageLocation.resolve(originalFileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()){
                return  resource;
            } else {
                throw new FileStorageException("File not found " + originalFileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileStorageException("File not found " + originalFileName, ex);
        }
    }

    public void deleteFile(String originalFileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(originalFileName).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            log.error("Could not delete file " + originalFileName, ex);
        }
    }

    public void validateFile(MultipartFile file){
        if(file.isEmpty()){
            throw new FileStorageException("Cannot store empty file");
        }
        if(file.getSize()> imagesStorageProperties.getMaxFileSize()){
            throw new FileStorageException("File size exceeds maximum allowed size");
        }
        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!Arrays.asList(imagesStorageProperties.getAllowedExtensions()).contains(fileExtension.toLowerCase())){
            throw new FileStorageException("File type not allowed: " + fileExtension);
        }
    }

    private String getFileExtension(String originalFileName) {
        if (originalFileName == null || originalFileName.isEmpty()) {
            return "";
        }
        int dotIndex = originalFileName.lastIndexOf('.');
        return dotIndex >= 0 ? originalFileName.substring(dotIndex + 1) : "";
    }


}
