//package com.example.HR.controllerTest;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//@ActiveProfiles("test")
//@Transactional
//public class MultipartConfigTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void testMultipartConfigurationLoads() throws Exception {
//        // Test that the application starts successfully with our multipart configuration
//        // This test verifies that our TomcatConfig and MultipartConfig don't cause startup issues
//
//        MockMultipartFile file = new MockMultipartFile(
//                "multipartFile",
//                "test.jpg",
//                "image/jpeg",
//                "test image content".getBytes()
//        );
//
//        MockMultipartFile employeeData = new MockMultipartFile(
//                "employeeDTO",
//                "",
//                "application/json",
//                "{\"fullname\":\"Test User\",\"username\":\"testuser\"}".getBytes()
//        );
//
//        // This should not throw FileCountLimitExceededException anymore
//        mockMvc.perform(multipart("/employee/add")
//                        .file(file)
//                        .file(employeeData))
//                .andDo(result -> {
//                    System.out.println("Response Status: " + result.getResponse().getStatus());
//                    System.out.println("Response Body: " + result.getResponse().getContentAsString());
//                    if (result.getResolvedException() != null) {
//                        System.out.println("Exception Type: " + result.getResolvedException().getClass().getSimpleName());
//                        System.out.println("Exception Message: " + result.getResolvedException().getMessage());
//                    }
//                })
//                // We expect this to fail with validation or business logic errors,
//                // but NOT with FileCountLimitExceededException
//                .andExpect(result -> {
//                    if (result.getResolvedException() != null) {
//                        String exceptionName = result.getResolvedException().getClass().getSimpleName();
//                        // Verify we don't get FileCountLimitExceededException
//                        assert !exceptionName.contains("FileCountLimitExceeded") :
//                            "Should not get FileCountLimitExceededException with our configuration";
//                    }
//                });
//    }
//
//    @Test
//    void testMultipleFormFields() throws Exception {
//        // Test with multiple form fields to ensure file count limit is properly configured
//
//        MockMultipartFile file1 = new MockMultipartFile(
//                "multipartFile",
//                "test1.jpg",
//                "image/jpeg",
//                "test image content 1".getBytes()
//        );
//
//        MockMultipartFile employeeData = new MockMultipartFile(
//                "employeeDTO",
//                "",
//                "application/json",
//                "{\"fullname\":\"Test User\",\"username\":\"testuser\",\"email\":\"test@test.com\"}".getBytes()
//        );
//
//        // Add additional form fields to test file count limit
//        MockMultipartFile additionalField1 = new MockMultipartFile(
//                "field1",
//                "",
//                "text/plain",
//                "value1".getBytes()
//        );
//
//        MockMultipartFile additionalField2 = new MockMultipartFile(
//                "field2",
//                "",
//                "text/plain",
//                "value2".getBytes()
//        );
//
//        mockMvc.perform(multipart("/employee/add")
//                        .file(file1)
//                        .file(employeeData)
//                        .file(additionalField1)
//                        .file(additionalField2))
//                .andDo(result -> {
//                    System.out.println("Multiple fields test - Response Status: " + result.getResponse().getStatus());
//                    if (result.getResolvedException() != null) {
//                        System.out.println("Exception Type: " + result.getResolvedException().getClass().getSimpleName());
//                        System.out.println("Exception Message: " + result.getResolvedException().getMessage());
//
//                        // Verify we don't get FileCountLimitExceededException
//                        String exceptionName = result.getResolvedException().getClass().getSimpleName();
//                        assert !exceptionName.contains("FileCountLimitExceeded") :
//                            "Should not get FileCountLimitExceededException with multiple form fields";
//                    }
//                });
//    }
//}