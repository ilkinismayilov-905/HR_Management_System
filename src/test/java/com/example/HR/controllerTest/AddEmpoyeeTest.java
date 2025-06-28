package com.example.HR.controllerTest;

import com.example.HR.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class AddEmpoyeeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createEmployee() throws Exception{
        Map<String,Object> employeeDto = new HashMap<>();

        User userName = new User();
        userName.setId(1L);
        userName.setUsername("john_doe"); // istəyə görə doldur

        User email = new User();
        email.setId(2L);
        email.setEmail("john.doe@example.com"); // istəyə görə doldur

        User password = new User();
        password.setId(3L);
        password.setPassword("securePassword"); // istəyə görə doldur

        User confirmPassword = new User();
        confirmPassword.setId(3L);
        confirmPassword.setPassword("securePassword");

        employeeDto.put("fullname","Ilkin");
        employeeDto.put("employeeId", "EMP123456");
        employeeDto.put("joinDate", "2023-01-15");

        employeeDto.put("userName", userName);
        employeeDto.put("email", email);
        employeeDto.put("password", password);
        employeeDto.put("confirmPassword", confirmPassword);

        employeeDto.put("phoneNumber", "+994501234567");
        employeeDto.put("company", "TechCorp LLC");
        employeeDto.put("departament", "HR");
        employeeDto.put("jobTitle", "DEVELOPER");
        employeeDto.put("about", "Experienced HR manager");
        employeeDto.put("employmentType", "FULL_TIME");
        employeeDto.put("status", "ACTIVE");

        String employee = new ObjectMapper().writeValueAsString(employeeDto);

        mockMvc.perform(post("/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.emloyeeId").value("EMP123456"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullname").value("Ilkin"));
    }
}
