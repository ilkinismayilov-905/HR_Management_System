package com.example.HR.controllerTest;

import com.example.HR.entity.User;
import com.example.HR.entity.employee.Employee;
import com.example.HR.enums.Departament;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import com.example.HR.enums.UserRoles;
import com.example.HR.repository.EmployeeRepository;
import com.example.HR.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AddEmployeeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    @BeforeEach
    void setUp() {
        // Clean up both repositories
        employeeRepository.deleteAll();
        userRepository.deleteAll();

        // Create test user
        User user = new User();
        user.setUsername("ilkin905");
        user.setEmail("ism@gmail.com");
        user.setPassword("ilk123456");
        user.setConfirmPassword("ilk123456");
        user.setRoles(UserRoles.USER);

        userRepository.save(user);
    }

    @Test
    void createEmployee() throws Exception {
        // Verify user exists before test
        assertTrue(userRepository.findByUsername("ilkin905").isPresent(), "Test user should exist");

        Map<String, Object> employeeDto = new HashMap<>();

        employeeDto.put("fullname", "Ilkin");
        employeeDto.put("employeeId", "EMP123456");
        employeeDto.put("joinDate", "2023-01-15");
        employeeDto.put("username", "ilkin905");
        employeeDto.put("email", "ism@gmail.com");
        employeeDto.put("password", "ilk123456");
        employeeDto.put("confirmPassword", "ilk123456");
        employeeDto.put("phoneNumber", "+994501234567");
        employeeDto.put("company", "TechCorp LLC");
        employeeDto.put("departament", "IT");
        employeeDto.put("jobTitle", "DEVELOPER");
        employeeDto.put("about", "Experienced HR manager");
        employeeDto.put("employmentType", "FULL_TIME");
        employeeDto.put("status", "ACTIVE");

        String employeeJson = objectMapper.writeValueAsString(employeeDto);

        // First, just test if the endpoint responds
        mockMvc.perform(post("/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andDo(result1 -> {
                    System.out.println("Response Status: " + result1.getResponse().getStatus());
                    System.out.println("Response Body: " + result1.getResponse().getContentAsString());
                    if (result1.getResolvedException() != null) {
                        System.out.println("Exception: " + result1.getResolvedException().getMessage());
                        result1.getResolvedException().printStackTrace();
                    }
                })
                .andExpect(status().isCreated());
    }

    @Test
    void createEmployee_UserNotFound() throws Exception {
        Map<String, Object> employeeDto = new HashMap<>();

        employeeDto.put("fullname", "Ilkin");
        employeeDto.put("employeeId", "EMP123456");
        employeeDto.put("joinDate", "2023-01-15");
        employeeDto.put("username", "nonexistent");
        employeeDto.put("email", "nonexistent@gmail.com");
        employeeDto.put("password", "password");
        employeeDto.put("confirmPassword", "password");
        employeeDto.put("phoneNumber", "+994501234567");
        employeeDto.put("company", "TechCorp LLC");
        employeeDto.put("departament", "IT");
        employeeDto.put("jobTitle", "DEVELOPER");
        employeeDto.put("about", "Experienced HR manager");
        employeeDto.put("employmentType", "FULL_TIME");
        employeeDto.put("status", "ACTIVE");

        String employee = objectMapper.writeValueAsString(employeeDto);

        mockMvc.perform(post("/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee))
                .andExpect(status().isNotFound());
    }

    @Test
    void createEmployee_InvalidData() throws Exception {
        Map<String, Object> employeeDto = new HashMap<>();

        // Missing required fields
        employeeDto.put("fullname", "");
        employeeDto.put("employeeId", "");
        employeeDto.put("username", "ilkin905");

        String employee = objectMapper.writeValueAsString(employeeDto);

        mockMvc.perform(post("/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUserExists() {
        // Simple test to verify user creation works
        assertTrue(userRepository.findByUsername("ilkin905").isPresent(), "User should exist after setUp");
        User user = userRepository.findByUsername("ilkin905").get();
        assertEquals("ism@gmail.com", user.getEmail());
        assertEquals("ilk123456", user.getPassword());
    }
}
