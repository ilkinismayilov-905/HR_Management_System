package com.example.HR.service.implement;

import com.example.HR.converter.Convert;
import com.example.HR.dto.EmployeeDTO;
import com.example.HR.entity.Employee;
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
            log.info("There is no Employee");
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

}
