package com.example.HR;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = "employee-photos";
        Path employeePhotosDir = Paths.get(location);

        String employeePhotosPath = employeePhotosDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/"+location+"**")
                .addResourceLocations("file:"+employeePhotosPath+"/");
    }
}
