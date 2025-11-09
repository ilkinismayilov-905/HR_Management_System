package com.example.HR.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "client.images")
@Data
public class ClientImageStorageProperties {
    private String uploadDir = "clientImage";
    private Long maxFileSize = 10485760L;
    private String[] allowedExtensions = {"jpg", "jpeg", "png", "gif", "pdf", "doc", "docx", "txt"};
}
