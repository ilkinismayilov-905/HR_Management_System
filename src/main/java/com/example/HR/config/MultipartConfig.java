package com.example.HR.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration
public class MultipartConfig {

    /**
     * Configure multipart resolver to handle file uploads
     */
    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
        resolver.setResolveLazily(true);
        return resolver;
    }

    /**
     * Configure multipart settings
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        // Set maximum file size (10MB)
        factory.setMaxFileSize(DataSize.ofMegabytes(10));

        // Set maximum request size (100MB)
        factory.setMaxRequestSize(DataSize.ofMegabytes(100));

        // Set file size threshold (2KB)
        factory.setFileSizeThreshold(DataSize.ofKilobytes(2));

        return factory.createMultipartConfig();
    }
}