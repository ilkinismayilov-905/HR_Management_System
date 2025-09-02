package com.example.HR.service.implement;

import com.example.HR.config.FileStorageProperties;
import com.example.HR.entity.ticket.Ticket;
import com.example.HR.entity.ticket.TicketAttachment;
import com.example.HR.exception.FileStorageException;
import com.example.HR.repository.ticket.TicketAttachmentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
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
public class FileStorageService {
    private final FileStorageProperties fileStorageProperties;
    private final TicketAttachmentRepository attachmentRepository;

    private Path fileStorageLocation;

    @PostConstruct
    public void init(){
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }

    public TicketAttachment storeFile(MultipartFile file, Ticket ticket){
        validateFile(file);

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(originalFileName);
        String fileName = UUID.randomUUID().toString() + "." + fileExtension;

        try {
            if (originalFileName.contains("..")){
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + originalFileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            TicketAttachment attachment = TicketAttachment.builder()
                    .fileName(fileName)
                    .originalFileName(originalFileName)
                    .filePath(targetLocation.toString())
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .ticket(ticket)
                    .build();

            return attachmentRepository.save(attachment);
        } catch (Exception e) {
            throw new FileStorageException("Could not store file " + originalFileName + ". Please try again!", e);
        }
    }

    public Resource loadFileAsResource(String fileName){
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()){
                return  resource;
            } else {
                throw new FileStorageException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
        throw new FileStorageException("File not found " + fileName, ex);
        }
    }

    public void deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            log.error("Could not delete file " + fileName, ex);
        }
    }

    public void validateFile(MultipartFile file){
        if(file.isEmpty()){
            throw new FileStorageException("Cannot store empty file");
        }
        if(file.getSize()> fileStorageProperties.getMaxFileSize()){
            throw new FileStorageException("File size exceeds maximum allowed size");
        }
        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!Arrays.asList(fileStorageProperties.getAllowedExtensions()).contains(fileExtension.toLowerCase())){
            throw new FileStorageException("File type not allowed: " + fileExtension);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex >= 0 ? fileName.substring(dotIndex + 1) : "";
    }


}
