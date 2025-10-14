//package com.example.HR.repository;
//
//import com.example.HR.entity.user.User;
//import com.example.HR.entity.employee.Employee;
//import com.example.HR.enums.Departament;
//import com.example.HR.enums.EmploymentType;
//import com.example.HR.enums.JobTitle;
//import com.example.HR.enums.Status;
//import com.example.HR.enums.UserRoles;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@ActiveProfiles("test")
//@Transactional
//public class EmployeeRepositoryTest {
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private User testUser1;
//    private User testUser2;
//    private Employee testEmployee1;
//    private Employee testEmployee2;
//
//    @BeforeEach
//    void setUp() {
//        // Clean up repositories
//        employeeRepository.deleteAll();
//        userRepository.deleteAll();
//
//        // Create test users
//        testUser1 = new User();
//        testUser1.setUsername("john.doe");
//        testUser1.setEmail("john.doe@example.com");
//        testUser1.setPassword("password123");
//        testUser1.setConfirmPassword("password123");
//        testUser1.setRoles(UserRoles.USER);
//        testUser1 = userRepository.save(testUser1);
//
//        testUser2 = new User();
//        testUser2.setUsername("jane.smith");
//        testUser2.setEmail("jane.smith@example.com");
//        testUser2.setPassword("password456");
//        testUser2.setConfirmPassword("password456");
//        testUser2.setRoles(UserRoles.USER);
//        testUser2 = userRepository.save(testUser2);
//
//        // Create test employees
//        testEmployee1 = new Employee();
//        testEmployee1.setFullname("John Doe");
//        testEmployee1.setEmployeeId("EMP001");
//        testEmployee1.setJoinDate(LocalDate.of(2023, 1, 15));
//        testEmployee1.setPhoneNumber("+994501234567");
//        testEmployee1.setCompany("TechCorp LLC");
//        testEmployee1.setDepartament(Departament.IT);
//        testEmployee1.setJobTitle(JobTitle.DEVELOPER);
//        testEmployee1.setEmploymentType(EmploymentType.FULL_TIME);
//        testEmployee1.setStatus(Status.ACTIVE);
//        testEmployee1.setAbout("Experienced developer");
//        testEmployee1.setUsername(testUser1);
//        testEmployee1.setEmail(testUser1);
//        testEmployee1.setPassword(testUser1);
//        testEmployee1 = employeeRepository.save(testEmployee1);
//
//        testEmployee2 = new Employee();
//        testEmployee2.setFullname("Jane Smith");
//        testEmployee2.setEmployeeId("EMP002");
//        testEmployee2.setJoinDate(LocalDate.of(2023, 2, 20));
//        testEmployee2.setPhoneNumber("+994507654321");
//        testEmployee2.setCompany("TechCorp LLC");
//        testEmployee2.setDepartament(Departament.HR);
//        testEmployee2.setJobTitle(JobTitle.CEO);
//        testEmployee2.setEmploymentType(EmploymentType.FULL_TIME);
//        testEmployee2.setStatus(Status.ACTIVE);
//        testEmployee2.setAbout("CEO");
//        testEmployee2.setUsername(testUser2);
//        testEmployee2.setEmail(testUser2);
//        testEmployee2.setPassword(testUser2);
//        testEmployee2 = employeeRepository.save(testEmployee2);
//    }
//
//    @Test
//    void testFindAll() {
//        List<Employee> employees = employeeRepository.findAll();
//
//        assertNotNull(employees);
//        assertEquals(2, employees.size());
//        assertTrue(employees.stream().anyMatch(e -> e.getFullname().equals("John Doe")));
//        assertTrue(employees.stream().anyMatch(e -> e.getFullname().equals("Jane Smith")));
//    }
//
//    @Test
//    void testFindById() {
//        Optional<Employee> foundEmployee = employeeRepository.findById(testEmployee1.getId());
//
//        assertTrue(foundEmployee.isPresent());
//        assertEquals("John Doe", foundEmployee.get().getFullname());
//        assertEquals("EMP001", foundEmployee.get().getEmployeeId());
//    }
//
//    @Test
//    void testFindById_NotFound() {
//        Optional<Employee> foundEmployee = employeeRepository.findById(999L);
//
//        assertFalse(foundEmployee.isPresent());
//    }
//
//    @Test
//    void testSave() {
//        User newUser = new User();
//        newUser.setUsername("new.user");
//        newUser.setEmail("new.user@example.com");
//        newUser.setPassword("password789");
//        newUser.setConfirmPassword("password789");
//        newUser.setRoles(UserRoles.USER);
//        newUser = userRepository.save(newUser);
//
//        Employee newEmployee = new Employee();
//        newEmployee.setFullname("New Employee");
//        newEmployee.setEmployeeId("EMP003");
//        newEmployee.setJoinDate(LocalDate.now());
//        newEmployee.setPhoneNumber("+994509876543");
//        newEmployee.setCompany("TechCorp LLC");
//        newEmployee.setDepartament(Departament.MARKETING);
//        newEmployee.setJobTitle(JobTitle.PROJECT_MANAGER);
//        newEmployee.setEmploymentType(EmploymentType.PART_TIME);
//        newEmployee.setStatus(Status.ACTIVE);
//        newEmployee.setAbout("PROJECT_MANAGER");
//        newEmployee.setUsername(newUser);
//        newEmployee.setEmail(newUser);
//        newEmployee.setPassword(newUser);
//
//        Employee savedEmployee = employeeRepository.save(newEmployee);
//
//        assertNotNull(savedEmployee.getId());
//        assertEquals("New Employee", savedEmployee.getFullname());
//        assertEquals("EMP003", savedEmployee.getEmployeeId());
//    }
//
//    @Test
//    void testDeleteById() {
//        assertTrue(employeeRepository.existsById(testEmployee1.getId()));
//
//        employeeRepository.deleteById(testEmployee1.getId());
//
//        assertFalse(employeeRepository.existsById(testEmployee1.getId()));
//        assertEquals(1, employeeRepository.findAll().size());
//    }
//
//    @Test
//    void testExistsById() {
//        assertTrue(employeeRepository.existsById(testEmployee1.getId()));
//        assertFalse(employeeRepository.existsById(999L));
//    }
//
//    @Test
//    void testGetEmployeeByStatus() {
//        List<Employee> activeEmployees = employeeRepository.getEmployeeByStatus(Status.ACTIVE);
//
//        assertNotNull(activeEmployees);
//        assertEquals(2, activeEmployees.size());
//        assertTrue(activeEmployees.stream().allMatch(e -> e.getStatus() == Status.ACTIVE));
//    }
//
//    @Test
//    void testGetEmployeeByStatus_Empty() {
//        List<Employee> inactiveEmployees = employeeRepository.getEmployeeByStatus(Status.INACTIVE);
//
//        assertNotNull(inactiveEmployees);
//        assertTrue(inactiveEmployees.isEmpty());
//    }
//
//    @Test
//    void testGetEmployeeByJobTitle() {
//        List<Employee> developers = employeeRepository.getEmployeeByJobTitle(JobTitle.DEVELOPER);
//
//        assertNotNull(developers);
//        assertEquals(1, developers.size());
//        assertEquals(JobTitle.DEVELOPER, developers.get(0).getJobTitle());
//        assertEquals("John Doe", developers.get(0).getFullname());
//    }
//
//    @Test
//    void testGetEmployeeByJobTitle_Empty() {
//        List<Employee> designers = employeeRepository.getEmployeeByJobTitle(JobTitle.DEVELOPER);
//
//        assertNotNull(designers);
//        assertTrue(designers.isEmpty());
//    }
//
//    @Test
//    void testGetEmployeeByFullname() {
//        List<Employee> johnEmployees = employeeRepository.getEmployeeByFullname("John Doe");
//
//        assertNotNull(johnEmployees);
//        assertEquals(1, johnEmployees.size());
//        assertEquals("John Doe", johnEmployees.get(0).getFullname());
//    }
//
//    @Test
//    void testGetEmployeeByFullname_CaseInsensitive() {
//        List<Employee> johnEmployees = employeeRepository.getEmployeeByFullname("john doe");
//
//        assertNotNull(johnEmployees);
//        assertEquals(1, johnEmployees.size());
//        assertEquals("John Doe", johnEmployees.get(0).getFullname());
//    }
//
//    @Test
//    void testGetEmployeeByFullname_PartialMatch() {
//        List<Employee> johnEmployees = employeeRepository.getEmployeeByFullname("John");
//
//        assertNotNull(johnEmployees);
//        assertEquals(1, johnEmployees.size());
//        assertTrue(johnEmployees.get(0).getFullname().contains("John"));
//    }
//
//    @Test
//    void testGetEmployeeByFullname_Empty() {
//        List<Employee> nonExistentEmployees = employeeRepository.getEmployeeByFullname("Non Existent");
//
//        assertNotNull(nonExistentEmployees);
//        assertTrue(nonExistentEmployees.isEmpty());
//    }
//
//    @Test
//    void testGetEmployeeByEmploymentType() {
//        List<Employee> fullTimeEmployees = employeeRepository.getEmployeeByEmploymentType(EmploymentType.FULL_TIME);
//
//        assertNotNull(fullTimeEmployees);
//        assertEquals(2, fullTimeEmployees.size());
//        assertTrue(fullTimeEmployees.stream().allMatch(e -> e.getEmploymentType() == EmploymentType.FULL_TIME));
//    }
//
//    @Test
//    void testGetEmployeeByEmploymentType_Empty() {
//        List<Employee> partTimeEmployees = employeeRepository.getEmployeeByEmploymentType(EmploymentType.PART_TIME);
//
//        assertNotNull(partTimeEmployees);
//        assertTrue(partTimeEmployees.isEmpty());
//    }
//
//    @Test
//    void testGetEmployeeByJoinDate() {
//        LocalDate joinDate = LocalDate.of(2023, 1, 15);
//        List<Employee> employeesJoinedOnDate = employeeRepository.getEmployeeByJoinDate(joinDate);
//
//        assertNotNull(employeesJoinedOnDate);
//        assertEquals(1, employeesJoinedOnDate.size());
//        assertEquals(joinDate, employeesJoinedOnDate.get(0).getJoinDate());
//        assertEquals("John Doe", employeesJoinedOnDate.get(0).getFullname());
//    }
//
//    @Test
//    void testGetEmployeeByJoinDate_Empty() {
//        LocalDate nonExistentDate = LocalDate.of(2024, 1, 1);
//        List<Employee> employeesJoinedOnDate = employeeRepository.getEmployeeByJoinDate(nonExistentDate);
//
//        assertNotNull(employeesJoinedOnDate);
//        assertTrue(employeesJoinedOnDate.isEmpty());
//    }
//
//    @Test
//    void testFindByUsername() {
//        Employee foundEmployee = employeeRepository.findByUsername(testUser1);
//
//        assertNotNull(foundEmployee);
//        assertEquals(testUser1, foundEmployee.getUsername());
//        assertEquals("John Doe", foundEmployee.getFullname());
//    }
//
//    @Test
//    void testFindByUsername_NotFound() {
//        User nonExistentUser = new User();
//        nonExistentUser.setId(999L);
//
//        Employee foundEmployee = employeeRepository.findByUsername(nonExistentUser);
//
//        assertNull(foundEmployee);
//    }
//
//    @Test
//    void testFindByEmail() {
//        Employee foundEmployee = employeeRepository.findByEmail(testUser1);
//
//        assertNotNull(foundEmployee);
//        assertEquals(testUser1, foundEmployee.getEmail());
//        assertEquals("John Doe", foundEmployee.getFullname());
//    }
//
//    @Test
//    void testFindByEmail_NotFound() {
//        User nonExistentUser = new User();
//        nonExistentUser.setId(999L);
//
//        Employee foundEmployee = employeeRepository.findByEmail(nonExistentUser);
//
//        assertNull(foundEmployee);
//    }
//
//    @Test
//    void testFindByPassword() {
//        Employee foundEmployee = employeeRepository.findByPassword(testUser1);
//
//        assertNotNull(foundEmployee);
//        assertEquals(testUser1, foundEmployee.getPassword());
//        assertEquals("John Doe", foundEmployee.getFullname());
//    }
//
//    @Test
//    void testFindByPassword_NotFound() {
//        User nonExistentUser = new User();
//        nonExistentUser.setId(999L);
//
//        Employee foundEmployee = employeeRepository.findByPassword(nonExistentUser);
//
//        assertNull(foundEmployee);
//    }
//}