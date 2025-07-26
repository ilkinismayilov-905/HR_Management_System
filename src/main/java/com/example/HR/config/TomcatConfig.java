//package com.example.HR.config;
//
//import org.apache.catalina.Context;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class TomcatConfig {
//
//    @Bean
//    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
//        return factory -> factory.addContextCustomizers(context -> {
//            // Allow casual multipart parsing
//            context.setAllowCasualMultipartParsing(true);
//
//            // Set file count limit to a higher value (default is 1)
//            context.getServletContext().setAttribute(
//                    "org.apache.tomcat.util.http.fileupload.FileUploadBase.fileCountMax", 100
//            );
//
//            // Set file size limit (in bytes) - 10MB per file
//            context.getServletContext().setAttribute(
//                    "org.apache.tomcat.util.http.fileupload.FileUploadBase.fileSizeMax", 10 * 1024 * 1024L
//            );
//
//            // Set total request size limit (in bytes) - 100MB total
//            context.getServletContext().setAttribute(
//                    "org.apache.tomcat.util.http.fileupload.FileUploadBase.sizeMax", 100 * 1024 * 1024L
//            );
//        });
//    }
//}