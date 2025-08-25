package com.example.HR.service.implement;

import com.example.HR.converter.EmployeeConverter;
import com.example.HR.dto.EmployeeInformationDTO;
import com.example.HR.dto.employee.EmployeeRequestDTO;
import com.example.HR.dto.employee.EmployeeResponseDTO;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final EmployeeConverter employeeConverter = new EmployeeConverter();

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
    public EmployeeRequestDTO save(EmployeeRequestDTO employeeRequestDTO) throws IOException {

//        validCheck(employeeRequestDTO);

//        log.info("Employee saved: {}" ,employeeRequestDTO.getFullname());

        // Find existing User by username, email, and password
        User existingUser = userRepository.findByFullname(employeeRequestDTO.getFullname())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + employeeRequestDTO.getFullname()));

        // Verify that the found user has matching email and password
        if (!existingUser.getEmail().equals(employeeRequestDTO.getEmail())) {
            throw new ValidException("Email mismatch for user: " + employeeRequestDTO.getFullname());
        }

        if (!existingUser.getPassword().equals(employeeRequestDTO.getPassword())) {
            throw new ValidException("Password mismatch for user: " + employeeRequestDTO.getFullname());
        }

        // Check if an employee already exists for this user
//        if (employeeRepository.findByFullname(existingUser) != null ||
//                employeeRepository.findByEmail(existingUser) != null ||
//                employeeRepository.findByPassword(existingUser) != null) {
//            throw new ValidException("Employee already exists for user: " + employeeRequestDTO.getFullname());
//        }
        Optional<Employee> existingEmployee = employeeRepository.findByFullname(existingUser);
        if (existingEmployee.isPresent()) {
            throw new ValidException("Employee already exists for user: " + employeeRequestDTO.getFullname());
        }

        // Create Employee object using converter
        Employee employee = employeeConverter.dtoToEntity(employeeRequestDTO);

        // Set the existing User object for all three relationships
        employee.setFullname(existingUser);
        employee.setEmail(existingUser);
        employee.setPassword(existingUser);

        // Save the employee
        Employee savedEmployee = employeeRepository.save(employee);

        // Return the saved employee as DTO
        return employeeConverter.entityToDto(savedEmployee);

    }

    @Override
    public Optional<EmployeeResponseDTO> getById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
//            log.info("Employee was found with id: {}" + id);
            return optionalEmployee
                    .map(employeeConverter::entityToResponseDTO);
        }else {
//            log.warn("There is no Employee with this ID");
            throw new NoIDException("There is no Employee with this ID");
        }
    }

    @Override
    public EmployeeRequestDTO update(Long id, EmployeeRequestDTO updatedDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found by id: " + id));

        // Use EmployeeConverter to update entity from DTO
        employeeConverter.updateEntityFromDto(updatedDto, employee);

        Employee savedEmployee = employeeRepository.save(employee);
        return employeeConverter.entityToDto(savedEmployee);
    }

    @Override
    public List<EmployeeResponseDTO> findAll() {
        List<Employee> employeeList = employeeRepository.findAll();

        return employeeConverter.entityListToResponseDTOList(employeeList);
    }

    @Override
    public List<EmployeeResponseDTO> getByStatus(Status status) {

        List<Employee> employeeStatus =employeeRepository.getEmployeeByStatus(status);

        if (employeeStatus == null || employeeStatus.isEmpty()) {
//            log.warn("No employees found with status: {}", status);
            throw new EmployeeNotFoundException("Employee list is empty for status: " + status);
        }

        return employeeConverter.entityListToResponseDTOList(employeeStatus);
    }

    @Override
    public List<EmployeeResponseDTO> getByJobPosition(JobTitle jobTitle) {
        List<Employee> employeeJob = employeeRepository.getEmployeeByJobTitle(jobTitle);

        if(employeeJob == null || employeeJob.isEmpty()){
//            log.warn("No employees found with job: {}", jobTitle);
            throw new EmployeeNotFoundException("Employee list is empty for job: " + jobTitle);
        }

        return employeeConverter.entityListToResponseDTOList(employeeJob);
    }

    @Override
    public List<EmployeeResponseDTO> getByEmploymentType(EmploymentType employmentType) {
        List<Employee> employee = employeeRepository.getEmployeeByEmploymentType(employmentType);

        if(employee == null || employee.isEmpty()){
//            log.warn("No employees found for type: {}", employmentType);
            throw new EmployeeNotFoundException("No employees found for type: " + employmentType);
        }

        return employeeConverter.entityListToResponseDTOList(employee);
    }

    @Override
    public Optional<EmployeeResponseDTO> getByFullname(String fullname) {

        Optional<User> userOptional = userRepository.findByFullname(fullname);

        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found with fullname: " + fullname);
        }


        Optional<Employee> employee = employeeRepository.findByFullname(userOptional.get());
//        if(employee.isPresent()){
//            Employee employee1 = employee.get();
//            return Optional.of(employeeConverter.entityToDto(employee1));
//        }

        if(employee.isEmpty()){
            throw new NotFoundException("There is no Employee by fullname: " + fullname);
        }
        else {
            return Optional.of(employeeConverter.entityToResponseDTO(employee.get()));
        }
    }

    @Override
    public List<EmployeeResponseDTO> getByDate(LocalDate localDate) {
        List<Employee> employeeList = employeeRepository.getEmployeeByJoinDate(localDate);

        if(employeeList == null || employeeList.isEmpty()){
//            log.warn("No employees found with date: {}", localDate);
            throw new EmployeeNotFoundException("No employees found for date: " + localDate);
        }
        return employeeConverter.entityListToResponseDTOList(employeeList);
    }

    @Override
    public Optional<EmployeeInformationDTO> getByEmployeeID(String employeeID){
        Employee employeeOpt = employeeRepository.findByEmployeeId(employeeID)
                .orElseThrow(() -> new NotFoundException("Employee not found by EmployeeID: {}" +employeeID));

        return Optional.ofNullable(employeeConverter.entityToEmployeeInfo(employeeOpt));

    }
}
