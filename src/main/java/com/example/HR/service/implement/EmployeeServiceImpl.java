package com.example.HR.service.implement;

import com.example.HR.converter.EmployeeConverter;
import com.example.HR.dto.EmployeeInformationDTO;
import com.example.HR.dto.employee.EmployeeRequestDTO;
import com.example.HR.dto.employee.EmployeeResponseDTO;
import com.example.HR.entity.User;
import com.example.HR.entity.employee.Employee;
import com.example.HR.entity.employee.EmployeeAttachment;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import com.example.HR.exception.*;
import com.example.HR.repository.employee.EmployeeRepository;
import com.example.HR.repository.UserRepository;
import com.example.HR.service.EmployeeService;
import com.example.HR.service.implement.fileStorage.EmployeeImagesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final EmployeeConverter employeeConverter = new EmployeeConverter();
    private final EmployeeImagesService imagesService;

    @Override
    public void deleteById(Long id) {
        if (employeeRepository.existsById(id)) {
            log.info("Employee deleted by ID: {}",id );
            employeeRepository.deleteById(id);
        }else {
            log.warn("There is no Employee with this ID");
            throw new NoIDException("There is no Employee with this ID");
        }

    }

    @Override
    public  EmployeeResponseDTO save(EmployeeRequestDTO employeeRequestDTO) throws IOException {
        log.info("Saving new Employee..");

        // Find existing User by username, email, and password
        User existingUser = userRepository.findByFullname(employeeRequestDTO.getFullname())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + employeeRequestDTO.getFullname()));
        log.info("User found by fullName: {}", employeeRequestDTO.getFullname());

        // Verify that the found user has matching email and password
        if (!existingUser.getEmail().equals(employeeRequestDTO.getEmail())) {
            throw new ValidException("Email mismatch for user: " + employeeRequestDTO.getFullname());
        }

        if (!existingUser.getPassword().equals(employeeRequestDTO.getPassword())) {
            throw new ValidException("Password mismatch for user: " + employeeRequestDTO.getFullname());
        }

        Optional<Employee> existingEmployee = employeeRepository.findByFullnameFullname(existingUser.getFullname());
        if (existingEmployee.isPresent()) {
            throw new ValidException("Employee already exists for user: " + employeeRequestDTO.getFullname());
        }

        // Create Employee object using converter
        Employee employee = employeeConverter.dtoToEntity(employeeRequestDTO);
        log.info("Employee converted");

        // Set the existing User object for all three relationships
        employee.setFullname(existingUser);
        employee.setEmail(existingUser);
        employee.setPassword(existingUser);

        // Save the employee
        Employee savedEmployee = employeeRepository.save(employee);
        savedEmployee.setEmployeeId(String.format("#TC-%03d", savedEmployee.getId()));
        log.info("Employee saved: {}" ,employeeRequestDTO.getFullname());

        // Return the saved employee as DTO
        return employeeConverter.entityToResponseDTO(savedEmployee);

    }

    @Override
    public EmployeeResponseDTO getById(Long id) {
        Employee optionalEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found by id: " + id));
        log.info("Employee found by ID: {}", id);

        return employeeConverter.entityToResponseDTO(optionalEmployee);
    }

    @Override
    public EmployeeResponseDTO update(Long id, EmployeeRequestDTO updatedDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found by id: " + id));
        log.info("Employee found by ID: {}", id);

        // Use EmployeeConverter to update entity from DTO
        employeeConverter.updateEntityFromDto(updatedDto, employee);
        log.info("Employee updated");

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee saved");
        return employeeConverter.entityToResponseDTO(savedEmployee);
    }

    @Override
    public List<EmployeeResponseDTO> findAll() {
        List<Employee> employeeList = employeeRepository.findAll();

        return employeeConverter.entityListToResponseDTOList(employeeList);
    }

    @Override
    public List<EmployeeResponseDTO> getByStatus(Status status) {

        List<Employee> employeeStatus =employeeRepository.getEmployeeByStatus(status);
        log.info("Employees found by status: {}", status);
        return employeeConverter.entityListToResponseDTOList(employeeStatus);
    }

    @Override
    public List<EmployeeResponseDTO> getByJobPosition(JobTitle jobTitle) {
        List<Employee> employeeJob = employeeRepository.getEmployeeByJobTitle(jobTitle);
        log.info("Employees found by JobTitle: {}", jobTitle);

        return employeeConverter.entityListToResponseDTOList(employeeJob);
    }

    @Override
    public List<EmployeeResponseDTO> getByEmploymentType(EmploymentType employmentType) {
        List<Employee> employee = employeeRepository.getEmployeeByEmploymentType(employmentType);
        log.info("Employees found by EmploymentType: {}", employmentType);

        return employeeConverter.entityListToResponseDTOList(employee);
    }

    @Override
    public EmployeeResponseDTO getByFullname(String fullname) {

        User userOptional = userRepository.findByFullname(fullname)
                .orElseThrow(() -> new NotFoundException("User not found by fullname: " + fullname));
        log.info("User found by fullName: {}", fullname);

        Employee employee = employeeRepository.findByFullnameFullname(userOptional.getFullname())
                .orElseThrow(() -> new NotFoundException("Employee not found by fullname"));
        log.info("Employee found by fullName: {}", fullname);

            return employeeConverter.entityToResponseDTO(employee);
    }

    @Override
    public List<EmployeeResponseDTO> getByDate(LocalDate localDate) {
        List<Employee> employeeList = employeeRepository.getEmployeeByJoinDate(localDate);
        log.info("User found by LocalDate: {}", localDate);
        return employeeConverter.entityListToResponseDTOList(employeeList);
    }

    @Override
    public EmployeeInformationDTO getByEmployeeID(String employeeID){
        Employee employeeOpt = employeeRepository.findByEmployeeId(employeeID)
                .orElseThrow(() -> new NotFoundException("Employee not found by EmployeeID: {}" +employeeID));
        log.info("Employee found by employeeID: {}", employeeID);

        return employeeConverter.entityToEmployeeInfo(employeeOpt);

    }

    @Override
    public EmployeeAttachment uploadAttachment(String employeeID, MultipartFile file) {
        Employee employee = employeeRepository.findByEmployeeId(employeeID)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeID));
        log.info("Employee found by employeeID: {}", employeeID);

        EmployeeAttachment attachment = imagesService.storeFile(file,employee);
        log.info("Uploaded attachment {} for ticket {}", file.getOriginalFilename(), employeeID);

        return attachment;
    }
}
