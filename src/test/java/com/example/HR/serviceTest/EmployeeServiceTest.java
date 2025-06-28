package com.example.HR.serviceTest;

//import com.example.HR.Convert;
import com.example.HR.converter.Convert;
import com.example.HR.dto.EmployeeDTO;
//import com.example.HR.entity.Employee;
import com.example.HR.entity.User;
import com.example.HR.entity.employee.Employee;
import com.example.HR.enums.*;
import com.example.HR.repository.EmployeeRepository;
import com.example.HR.service.implement.EmployeeServiceImpl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private Validator validator;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;



    @Test
    public void testSaveEmployee() throws IOException{

        User user = new User();
        user.setEmail("ilkin2006@gmail.com");
        user.setPassword("123456");
        user.setUsername("ilkin6666");
        user.setConfirmPassword("123456");


        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFullname("John Doe");
        employeeDTO.setEmployeeId("EMP123456");
        employeeDTO.setEmail(user);
        employeeDTO.setEmployeeId("EMP1");
        employeeDTO.setAbout("About");
        employeeDTO.setConfirmPassword(user);
        employeeDTO.setCompany("Company");
        employeeDTO.setPassword(user);
        employeeDTO.setDepartament(Departament.IT);
        employeeDTO.setUserName(user);

        try (MockedStatic<Convert> convertMock = mockStatic(Convert.class)) {
            Employee employee = new Employee();
            convertMock.when(() -> Convert.dtoToEntity(any(EmployeeDTO.class))).thenReturn(employee);

            when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

            EmployeeDTO result = employeeServiceImpl.save(employeeDTO);

            assertNotNull(result);
            convertMock.verify(() -> Convert.dtoToEntity(employeeDTO), times(1));
            verify(employeeRepository, times(1)).save(employee);
        }
    }
}
