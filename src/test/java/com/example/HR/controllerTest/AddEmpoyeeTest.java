package com.example.HR.controllerTest;

import com.example.HR.entity.User;
import com.example.HR.enums.UserRoles;
import com.example.HR.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("ilkin905");
        user.setEmail("ism@gmail.com");
        user.setPassword("ilk123456");
        user.setConfirmPassword("ilk123456");
        user.setRoles(UserRoles.USER);

        userRepository.save(user);
    }

    @Test
    void createEmployee() throws Exception{
        Map<String,Object> employeeDto = new HashMap<>();

        employeeDto.put("fullname","Ilkin");
        employeeDto.put("employeeId", "EMP123456");
        employeeDto.put("joinDate", "2023-01-15");

        employeeDto.put("username", "ilkin905");
        employeeDto.put("email", "ism@gmail.com");
        employeeDto.put("password", "ilk123456");
        employeeDto.put("confirmPassword", "ilk123456");

        employeeDto.put("phoneNumber", "+994501234567");
        employeeDto.put("company", "TechCorp LLC");
        employeeDto.put("departament", "HR");
        employeeDto.put("jobTitle", "DEVELOPER");
        employeeDto.put("about", "Experienced HR manager");
        employeeDto.put("employmentType", "FULL_TIME");
        employeeDto.put("status", "ACTIVE");

        String employee = objectMapper.writeValueAsString(employeeDto);

        mockMvc.perform(post("/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("ilkin905"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullname").value("Ilkin"));
    }
}
