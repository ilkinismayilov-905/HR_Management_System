package com.example.HR.controller;

import com.example.HR.dto.EmployeeDTO;
import com.example.HR.entity.employee.Employee;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import com.example.HR.service.EmployeeService;
//import com.example.HR.service.implement.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
            summary = "Create a new employee",
            description = "Creates a new employee with the provided information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/add")
    public ResponseEntity<?> createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) throws IOException {
        log.info("=== Employee Creation Request ===");
        log.info("Creating new employee: {}", employeeDTO.getFullname());
        log.info("Email received: '{}'", employeeDTO.getEmail());
        log.info("Username received: '{}'", employeeDTO.getUsername());
        log.info("Password received: '{}'", employeeDTO.getPassword());
        log.info("ConfirmPassword received: '{}'", employeeDTO.getConfirmPassword());
        log.info("EmployeeId received: '{}'", employeeDTO.getEmployeeId());
        log.info("PhoneNumber received: '{}'", employeeDTO.getPhoneNumber());
        log.info("Company received: '{}'", employeeDTO.getCompany());
        log.info("Departament received: '{}'", employeeDTO.getDepartament());
        log.info("JobTitle received: '{}'", employeeDTO.getJobTitle());
        log.info("EmploymentType received: '{}'", employeeDTO.getEmploymentType());
        log.info("Status received: '{}'", employeeDTO.getStatus());
        log.info("About received: '{}'", employeeDTO.getAbout());
        log.info("JoinDate received: '{}'", employeeDTO.getJoinDate());
        log.info("=================================");
        
        EmployeeDTO employee = employeeService.save(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Get all employees",
            description = "Returns a list of all employees"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No employees found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<EmployeeDTO>> viewAllEmployees() throws MalformedURLException {
        List<EmployeeDTO> employeeDTOList = employeeService.getAll();
//        log.info("All Employee list returned");

        return ResponseEntity.ok(employeeDTOList);
    }

    @Operation(summary = "Get employee by ID",
            description = "Returns a single employee by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return employeeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get employees by status",
            description = "Returns a list of all employees by status"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No employees found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getByStatus/{status}")
    public ResponseEntity<List<EmployeeDTO>> viewEmployeesByStatus(@PathVariable Status status) throws MalformedURLException {
        List<EmployeeDTO> employeeDTOList = employeeService.getByStatus(status);

//        log.info("Employee list returned by status: {}", status);

        return ResponseEntity.ok(employeeDTOList);
    }

    @Operation(summary = "Get employees by fullname",
            description = "Returns a list of all employees by fullname"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No employees found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getByFullname/{fullname}")
    public ResponseEntity<List<EmployeeDTO>> viewEmployeesByFullname(@PathVariable String fullname) throws MalformedURLException {
        List<EmployeeDTO> employeeDTOList = employeeService.getByFulName(fullname);

//        log.info("Employee list returned by fullname: {}" ,fullname);
        return ResponseEntity.ok(employeeDTOList);
    }

    @Operation(summary = "Get employees by jobTitle",
            description = "Returns a list of all employees by JobTitle"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No employees found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getByJob/{jobTitle}")
    public ResponseEntity<List<EmployeeDTO>> viewEmployeesByJobTitle(@PathVariable JobTitle jobTitle) throws MalformedURLException {
        List<EmployeeDTO> employeeDTOList = employeeService.getByJobPosition(jobTitle);

//        log.info("Employee list returned by jobTitle: {}", jobTitle);

        return ResponseEntity.ok(employeeDTOList);
    }

    @Operation(summary = "Get employees by employment type",
            description = "Returns a list of all employees by employment type"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No employees found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getByEmploymentType/{employmentType}")
    public ResponseEntity<List<EmployeeDTO>> viewEmployeesByEmploymentType(@PathVariable EmploymentType employmentType) throws MalformedURLException {
        List<EmployeeDTO> employeeDTOList = employeeService.getByEmploymentType(employmentType);

//        log.info("Employee list returned by employment type: {}", employmentType);

        return ResponseEntity.ok(employeeDTOList);
    }

    @Operation(summary = "Get employees by joinDate",
            description = "Returns a list of all employees by joinDate"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No employees found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getByDate/{localDate}")
    public ResponseEntity<List<EmployeeDTO>> viewEmployeesByJoinDate(@PathVariable LocalDate localDate) throws MalformedURLException {
        List<EmployeeDTO> employeeDTOList = employeeService.getByDate(localDate);

//        log.info("Employee list returned by joinDate: {}", localDate);

        return ResponseEntity.ok(employeeDTOList);
    }

    //UPDATE EMPLOYEE

    @Operation(summary = "Update employee",
            description = "Updates an existing employee"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeDTO employeeDTO) throws IOException {
        employeeService.update(id,employeeDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete employee",
            description = "Deletes an employee by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
