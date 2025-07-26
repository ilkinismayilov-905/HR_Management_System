package com.example.HR.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        // Enable pretty printing for debugging
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        // Register JavaTimeModule for LocalDate handling
        mapper.registerModule(new JavaTimeModule());
        
        // Disable FAIL_ON_UNKNOWN_PROPERTIES to be more lenient
        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        return mapper;
    }
} 