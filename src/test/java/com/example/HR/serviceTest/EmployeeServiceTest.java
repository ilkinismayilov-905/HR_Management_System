//package com.example.HR.serviceTest;
//
//import com.example.HR.converter.Convert;
//import com.example.HR.converter.EmployeeConverter;
//import com.example.HR.dto.employee.EmployeeRequestDTO;
//import com.example.HR.entity.user.User;
//import com.example.HR.entity.employee.Employee;
//import com.example.HR.enums.*;
//import com.example.HR.exception.NotFoundException;
//import com.example.HR.exception.ValidException;
//import com.example.HR.repository.employee.EmployeeRepository;
//import com.example.HR.repository.User.UserRepository;
//import com.example.HR.service.implement.EmployeeServiceImpl;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockedStatic;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class EmployeeServiceTest {
//
//    @Mock
//    private EmployeeConverter employeeConverter;
//
//    @Mock
//    private EmployeeRepository employeeRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private EmployeeServiceImpl employeeServiceImpl;
//
//
//
//    @Test
//    public void testSaveEmployee() throws IOException {
//        // Create test user
//        User user = new User();
//        user.setId(1L);
//        user.setEmail("ilkin2006@gmail.com");
//        user.setPassword("123456");
//        user.setUsername("ilkin6666");
//        user.setConfirmPassword("123456");
//        user.setRoles(UserRoles.USER);
//
//        // Create test employee DTO
//        EmployeeRequestDTO employeeDTO = new EmployeeRequestDTO();
//        employeeDTO.setFullname("John Doe");
//        employeeDTO.setEmployeeId("EMP123456");
//        employeeDTO.setEmail("ilkin2006@gmail.com");
//        employeeDTO.setAbout("About");
//        employeeDTO.setConfirmPassword("123456");
//        employeeDTO.setCompany("Company");
//        employeeDTO.setPassword("123456");
//        employeeDTO.setDepartament(Departament.IT);
//        employeeDTO.setUsername("ilkin6666");
//        employeeDTO.setJoinDate(LocalDate.now());
//        employeeDTO.setPhoneNumber("+994501234567");
//        employeeDTO.setJobTitle(JobTitle.DEVELOPER);
//        employeeDTO.setEmploymentType(EmploymentType.FULL_TIME);
//        employeeDTO.setStatus(Status.ACTIVE);
//
//        // Create test employee
//        Employee employee = new Employee();
//        employee.setId(1L);
//        employee.setFullname("John Doe");
//        employee.setEmployeeId("EMP123456");
//        employee.setUsername(user);
//        employee.setEmail(user);
//        employee.setPassword(user);
//
//        try (MockedStatic<Convert> convertMock = mockStatic(Convert.class)) {
//            // Mock Convert.dtoToEntity
//            convertMock.when(() -> employeeConverter.dtoToEntity(any(EmployeeRequestDTO.class))).thenReturn(employee);
//
//            // Mock Convert.entityToDto
//            convertMock.when(() -> employeeConverter.entityToDto(any(Employee.class))).thenReturn(employeeDTO);
//
//            // Mock user repository calls
//            when(userRepository.findByUsername("ilkin6666")).thenReturn(Optional.of(user));
//            when(employeeRepository.findByUsername(user)).thenReturn(null);
//            when(employeeRepository.findByEmail(user)).thenReturn(null);
//            when(employeeRepository.findByPassword(user)).thenReturn(null);
//
//            // Mock employee repository save
//            when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
//
//            // Execute the method
//            EmployeeRequestDTO result = employeeServiceImpl.save(employeeDTO);
//
//            // Verify the result
//            assertNotNull(result);
//            assertEquals("John Doe", result.getFullname());
//            assertEquals("EMP123456", result.getEmployeeId());
//
//            // Verify interactions
//            convertMock.verify(() -> employeeConverter.dtoToEntity(employeeDTO), times(1));
//            convertMock.verify(() -> employeeConverter.entityToDto(employee), times(1));
//            verify(userRepository, times(1)).findByUsername("ilkin6666");
//            verify(employeeRepository, times(1)).save(employee);
//        }
//    }
//
//    @Test
//    public void testSaveEmployee_UserNotFound() {
//        // Create test employee DTO
//        EmployeeRequestDTO employeeDTO = new EmployeeRequestDTO();
//        employeeDTO.setUsername("nonexistent");
//        employeeDTO.setEmail("test@test.com");
//        employeeDTO.setPassword("password");
//
//        // Mock user repository to return empty
//        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());
//
//        // Execute and verify exception
//        assertThrows(NotFoundException.class, () -> {
//            employeeServiceImpl.save(employeeDTO);
//        });
//
//        verify(userRepository, times(1)).findByUsername("nonexistent");
//    }
//
//    @Test
//    public void testSaveEmployee_EmailMismatch() {
//        // Create test user
//        User user = new User();
//        user.setUsername("testuser");
//        user.setEmail("correct@email.com");
//        user.setPassword("password");
//
//        // Create test employee DTO with different email
//        EmployeeRequestDTO employeeDTO = new EmployeeRequestDTO();
//        employeeDTO.setUsername("testuser");
//        employeeDTO.setEmail("wrong@email.com");
//        employeeDTO.setPassword("password");
//
//        // Mock user repository
//        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
//
//        // Execute and verify exception
//        assertThrows(ValidException.class, () -> {
//            employeeServiceImpl.save(employeeDTO);
//        });
//
//        verify(userRepository, times(1)).findByUsername("testuser");
//    }
//
//    @Test
//    public void testGetEmployeeById() {
//        Long employeeId = 1L;
//        Employee employee = new Employee();
//        employee.setId(employeeId);
//        employee.setFullname("John Doe");
//
//        EmployeeRequestDTO employeeDTO = new EmployeeRequestDTO();
//        employeeDTO.setFullname("John Doe");
//
//        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
//
//        try (MockedStatic<Convert> convertMock = mockStatic(Convert.class)) {
//            convertMock.when(() -> employeeConverter.entityToDto(employee)).thenReturn(employeeDTO);
//
//            Optional<EmployeeRequestDTO> result = employeeServiceImpl.getById(employeeId);
//
//            assertTrue(result.isPresent());
//            assertEquals("John Doe", result.get().getFullname());
//            verify(employeeRepository, times(1)).findById(employeeId);
//        }
//    }
//
//    @Test
//    public void testGetAllEmployees() throws IOException {
//        Employee employee1 = new Employee();
//        employee1.setId(1L);
//        employee1.setFullname("John Doe");
//
//        Employee employee2 = new Employee();
//        employee2.setId(2L);
//        employee2.setFullname("Jane Smith");
//
//        List<Employee> employees = List.of(employee1, employee2);
//
//        EmployeeRequestDTO dto1 = new EmployeeRequestDTO();
//        dto1.setFullname("John Doe");
//
//        EmployeeRequestDTO dto2 = new EmployeeRequestDTO();
//        dto2.setFullname("Jane Smith");
//
//        when(employeeRepository.findAll()).thenReturn(employees);
//
//        try (MockedStatic<Convert> convertMock = mockStatic(Convert.class)) {
//            convertMock.when(() -> employeeConverter.entityToDto(employee1)).thenReturn(dto1);
//            convertMock.when(() -> employeeConverter.entityToDto(employee2)).thenReturn(dto2);
//
//            List<EmployeeRequestDTO> result = employeeServiceImpl.getAll();
//
//            assertNotNull(result);
//            assertEquals(2, result.size());
//            assertEquals("John Doe", result.get(0).getFullname());
//            assertEquals("Jane Smith", result.get(1).getFullname());
//            verify(employeeRepository, times(1)).findAll();
//        }
//    }
//}
