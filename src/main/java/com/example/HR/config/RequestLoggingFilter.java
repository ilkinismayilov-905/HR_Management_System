package com.example.HR.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@Slf4j
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            
            // Only log for employee endpoints
            if (httpRequest.getRequestURI().contains("/employee/add")) {
                log.info("=== Raw Request Logging ===");
                log.info("Request URI: {}", httpRequest.getRequestURI());
                log.info("Content-Type: {}", httpRequest.getContentType());
                log.info("Method: {}", httpRequest.getMethod());
                
                // Log request body if it's JSON
                if (httpRequest.getContentType() != null && 
                    httpRequest.getContentType().contains("application/json")) {
                    
                    // Read the request body
                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader bufferedReader = null;
                    try {
                        InputStream inputStream = request.getInputStream();
                        if (inputStream != null) {
                            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            char[] charBuffer = new char[128];
                            int bytesRead = -1;
                            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                                stringBuilder.append(charBuffer, 0, bytesRead);
                            }
                        }
                    } catch (IOException ex) {
                        log.error("Error reading request body", ex);
                    } finally {
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException ex) {
                                log.error("Error closing bufferedReader", ex);
                            }
                        }
                    }
                    
                    String requestBody = stringBuilder.toString();
                    log.info("Request Body: {}", requestBody);
                    log.info("=== End Raw Request Logging ===");
                    
                    // Create a new request with the body
                    HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(httpRequest) {
                        @Override
                        public ServletInputStream getInputStream() throws IOException {
                            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody.getBytes());
                            return new ServletInputStream() {
                                @Override
                                public int read() throws IOException {
                                    return byteArrayInputStream.read();
                                }
                                
                                @Override
                                public boolean isFinished() {
                                    return byteArrayInputStream.available() == 0;
                                }
                                
                                @Override
                                public boolean isReady() {
                                    return true;
                                }
                                
                                @Override
                                public void setReadListener(ReadListener readListener) {
                                    // Not needed for this implementation
                                }
                            };
                        }
                        
                        @Override
                        public BufferedReader getReader() throws IOException {
                            return new BufferedReader(new InputStreamReader(this.getInputStream()));
                        }
                    };
                    
                    chain.doFilter(wrappedRequest, response);
                    return;
                }
            }
        }
        
        chain.doFilter(request, response);
    }
} 