package com.example.HR.config;

import org.apache.catalina.Context;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
        return factory -> factory.addContextCustomizers(context -> {
            context.setAllowCasualMultipartParsing(true);
            context.getServletContext().setAttribute(
                    "org.apache.tomcat.util.http.fileupload.FileUploadBase.fileCountMax", 100
            );
        });
    }
}