package com.example.HR.service.implement;

import com.example.HR.converter.Convert;
import com.example.HR.dto.EmployeeDTO;
import com.example.HR.entity.employee.Employee;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import com.example.HR.exception.EmptyListException;
import com.example.HR.exception.NoIDException;
import com.example.HR.exception.ValidException;
import com.example.HR.repository.EmployeeRepository;
import com.example.HR.service.EmployeeService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Validator;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final Validator validator;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, Validator validator) {
        this.employeeRepository = employeeRepository;
        this.validator = validator;
    }

    @Override
    public void deleteById(Long id) {
        if (employeeRepository.existsById(id)) {
            log.info("Deleted");
            employeeRepository.deleteById(id);
            }else {
            log.warn("There is no Employee with this ID");
            throw new NoIDException("There is no Employee with this ID");
        }

    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) throws IOException {

        validCheck(employeeDTO);

        log.info("Employee saved");
        Employee newEmployee = Convert.dtoToEntity(employeeDTO);
        employeeRepository.save(newEmployee);

        return new EmployeeDTO();

    }

    @Override
    public Optional<EmployeeDTO> getById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            log.info("Employee was found with id: " + id);
            Employee employee = optionalEmployee.get();
            EmployeeDTO dto = Convert.entityToDto(employee);
            return Optional.of(dto);
        }else {
            log.warn("There is no Employee with this ID");
            throw new NoIDException("There is no Employee with this ID");
        }
    }

    @Override
    public List<EmployeeDTO> getAll() throws MalformedURLException {
        List employeeList = new ArrayList();
        for(Employee employee:employeeRepository.findAll()){
            Convert.entityToDto(employee);
            employeeList.add(employee);
        }
        if(employeeList.isEmpty()){
            log.warn("There is no Employee");
            throw new EmptyListException("Employee list is empty");
        }
        return employeeList;
    }



    public void validCheck(EmployeeDTO employeeDTO){
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);

        if (!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder();
            for (ConstraintViolation<EmployeeDTO> violation : violations) {
                errorMsg.append(violation.getPropertyPath()).append(": ")
                        .append(violation.getMessage()).append("; ");
            }
            log.warn("Fields should be valid");
            throw new ValidException("Validation failed: " + errorMsg.toString());
        }
    }

    @Override
    public List<EmployeeDTO> getByStatus(Status status) {

        List<Employee> employeeStatus =employeeRepository.getEmployeeByStatus(status);

        if (employeeStatus == null || employeeStatus.isEmpty()) {
            log.warn("No employees found with status: {}", status);
            throw new EmptyListException("Employee list is empty for status: " + status);
        }

        return Convert.entityListToDtoList(employeeStatus);
    }

    @Override
    public List<EmployeeDTO> getByJobPosition(JobTitle jobTitle) {
        List<Employee> employeeJob = employeeRepository.getEmployeeByJobTitle(jobTitle);

        if(employeeJob == null || employeeJob.isEmpty()){
            log.warn("No employees found with job: {}", jobTitle);
            throw new EmptyListException("Employee list is empty for job: " + jobTitle);
        }

        return Convert.entityListToDtoList(employeeJob);
    }

    @Override
    public List<EmployeeDTO> getByEmploymentType(EmploymentType employmentType) {
        List<Employee> employee = employeeRepository.getEmployeeByEmploymentType(employmentType);

        if(employee == null || employee.isEmpty()){
            log.warn("No employees found with type: {}", employmentType);
            throw new EmptyListException("Employee list is empty for type: " + employmentType);
        }

        return Convert.entityListToDtoList(employee);
    }

    @Override
    public List<EmployeeDTO> getByFulName(String fullName) {
       List<Employee> employeeList = employeeRepository.getEmployeeByFullname(fullName);

       if(employeeList == null || employeeList.isEmpty()){
           log.warn("No employees found with fullname: {}", fullName);
           throw new EmptyListException("Employee list is empty for fullname: " + fullName);
       }
       return Convert.entityListToDtoList(employeeList);
    }

    @Override
    public List<EmployeeDTO> getByDate(LocalDate localDate) {
        List<Employee> employeeList = employeeRepository.getEmployeeByJoinDate(localDate);

        if(employeeList == null || employeeList.isEmpty()){
            log.warn("No employees found with date: {}", localDate);
            throw new EmptyListException("Employee list is empty for date: " + localDate);
        }
        return Convert.entityListToDtoList(employeeList);
    }
}
