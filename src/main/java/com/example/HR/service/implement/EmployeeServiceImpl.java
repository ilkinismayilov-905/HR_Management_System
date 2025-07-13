package com.example.HR.service.implement;

import com.example.HR.converter.Convert;
import com.example.HR.dto.EmployeeDTO;
import com.example.HR.entity.User;
import com.example.HR.entity.employee.Employee;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import com.example.HR.exception.EmployeeNotFoundException;
import com.example.HR.exception.NoIDException;
import com.example.HR.exception.NotFoundException;
import com.example.HR.exception.ValidException;
import com.example.HR.repository.EmployeeRepository;
import com.example.HR.repository.UserRepository;
import com.example.HR.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
//    private final Validator validator;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void deleteById(Long id) {
        if (employeeRepository.existsById(id)) {
//            log.info("Deleted");
            employeeRepository.deleteById(id);
        }else {
//            log.warn("There is no Employee with this ID");
            throw new NoIDException("There is no Employee with this ID");
        }

    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) throws IOException {

//        validCheck(employeeDTO);

//        log.info("Employee saved: {}" ,employeeDTO.getFullname());

        // Find existing User by username, email, and password
        User existingUser = userRepository.findByUsername(employeeDTO.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + employeeDTO.getUsername()));

        // Verify that the found user has matching email and password
        if (!existingUser.getEmail().equals(employeeDTO.getEmail())) {
            throw new ValidException("Email mismatch for user: " + employeeDTO.getUsername());
        }

        if (!existingUser.getPassword().equals(employeeDTO.getPassword())) {
            throw new ValidException("Password mismatch for user: " + employeeDTO.getUsername());
        }

        // Check if an employee already exists for this user
        if (employeeRepository.findByUsername(existingUser) != null ||
                employeeRepository.findByEmail(existingUser) != null ||
                employeeRepository.findByPassword(existingUser) != null) {
            throw new ValidException("Employee already exists for user: " + employeeDTO.getUsername());
        }

        // Create Employee object using converter
        Employee employee = Convert.dtoToEntity(employeeDTO);

        // Set the existing User object for all three relationships
        employee.setUsername(existingUser);
        employee.setEmail(existingUser);
        employee.setPassword(existingUser);

        // Save the employee
        Employee savedEmployee = employeeRepository.save(employee);

        // Return the saved employee as DTO
        return Convert.entityToDto(savedEmployee);

    }

    @Override
    public Optional<EmployeeDTO> getById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
//            log.info("Employee was found with id: {}" + id);
            return optionalEmployee
                    .map(Convert::entityToDto);
        }else {
//            log.warn("There is no Employee with this ID");
            throw new NoIDException("There is no Employee with this ID");
        }
    }

    @Override
    public List<EmployeeDTO> getAll() throws MalformedURLException {
        List<Employee> employeeList = employeeRepository.findAll();

        return employeeList.stream()
                .map(Convert::entityToDto)
                .collect(Collectors.toList());
    }



//    public void validCheck(EmployeeDTO employeeDTO){
//        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
//
//        if (!violations.isEmpty()) {
//            StringBuilder errorMsg = new StringBuilder();
//            for (ConstraintViolation<EmployeeDTO> violation : violations) {
//                errorMsg.append(violation.getPropertyPath()).append(": ")
//                        .append(violation.getMessage()).append("; ");
//            }
    ////            log.warn("Fields should be valid");
//            throw new ValidException("Validation failed: " + errorMsg.toString());
//        }
//    }

    @Override
    public List<EmployeeDTO> getByStatus(Status status) {

        List<Employee> employeeStatus =employeeRepository.getEmployeeByStatus(status);

        if (employeeStatus == null || employeeStatus.isEmpty()) {
//            log.warn("No employees found with status: {}", status);
            throw new EmployeeNotFoundException("Employee list is empty for status: " + status);
        }

        return Convert.entityListToDtoList(employeeStatus);
    }

    @Override
    public List<EmployeeDTO> getByJobPosition(JobTitle jobTitle) {
        List<Employee> employeeJob = employeeRepository.getEmployeeByJobTitle(jobTitle);

        if(employeeJob == null || employeeJob.isEmpty()){
//            log.warn("No employees found with job: {}", jobTitle);
            throw new EmployeeNotFoundException("Employee list is empty for job: " + jobTitle);
        }

        return Convert.entityListToDtoList(employeeJob);
    }

    @Override
    public List<EmployeeDTO> getByEmploymentType(EmploymentType employmentType) {
        List<Employee> employee = employeeRepository.getEmployeeByEmploymentType(employmentType);

        if(employee == null || employee.isEmpty()){
//            log.warn("No employees found for type: {}", employmentType);
            throw new EmployeeNotFoundException("No employees found for type: " + employmentType);
        }

        return Convert.entityListToDtoList(employee);
    }

    @Override
    public List<EmployeeDTO> getByFulName(String fullname) {

        if (fullname == null || fullname.trim().isEmpty()) {
            throw new IllegalArgumentException("Fullname must not be blank");
        }
        List<Employee> employeeList = employeeRepository.getEmployeeByFullname(fullname.trim());

        if(employeeList == null || employeeList.isEmpty()){
//           log.warn("No employees found for fullname: {}", fullname);
            throw new EmployeeNotFoundException("No employees found for fullname: " + fullname);
        }
        return employeeList.stream()
                .map(Convert::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getByDate(LocalDate localDate) {
        List<Employee> employeeList = employeeRepository.getEmployeeByJoinDate(localDate);

        if(employeeList == null || employeeList.isEmpty()){
//            log.warn("No employees found with date: {}", localDate);
            throw new EmployeeNotFoundException("No employees found for date: " + localDate);
        }
        return Convert.entityListToDtoList(employeeList);
    }
}
