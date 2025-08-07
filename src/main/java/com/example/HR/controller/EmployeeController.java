package com.example.HR.controller;

import com.example.HR.dto.EmployeeRequestDTO;
import com.example.HR.dto.EmployeeResponseDTO;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import com.example.HR.service.EmployeeService;
//import com.example.HR.service.implement.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> createEmployee(@RequestBody @Valid EmployeeRequestDTO employeeRequestDTO) throws IOException {
        log.info("=== Employee Creation Request ===");
        log.info("Creating new employee: {}", employeeRequestDTO.getFullname());
        log.info("Email received: '{}'", employeeRequestDTO.getEmail());
        log.info("Username received: '{}'", employeeRequestDTO.getFullname());
        log.info("Password received: '{}'", employeeRequestDTO.getPassword());
        log.info("ConfirmPassword received: '{}'", employeeRequestDTO.getConfirmPassword());
        log.info("EmployeeId received: '{}'", employeeRequestDTO.getEmployeeId());
        log.info("PhoneNumber received: '{}'", employeeRequestDTO.getPhoneNumber());
        log.info("Company received: '{}'", employeeRequestDTO.getCompany());
        log.info("Departament received: '{}'", employeeRequestDTO.getDepartament());
        log.info("JobTitle received: '{}'", employeeRequestDTO.getJobTitle());
        log.info("EmploymentType received: '{}'", employeeRequestDTO.getEmploymentType());
        log.info("Status received: '{}'", employeeRequestDTO.getStatus());
        log.info("About received: '{}'", employeeRequestDTO.getAbout());
        log.info("JoinDate received: '{}'", employeeRequestDTO.getJoinDate());
        log.info("=================================");
        
        EmployeeRequestDTO employee = employeeService.save(employeeRequestDTO);
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
    public ResponseEntity<List<EmployeeResponseDTO>> viewAllEmployees() throws MalformedURLException {
        List<EmployeeResponseDTO> employeeResponseDTOList = employeeService.findAll();
//        log.info("All Employee list returned");

        return ResponseEntity.ok(employeeResponseDTOList);
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
    public ResponseEntity<EmployeeRequestDTO> getEmployeeById(@PathVariable Long id) {
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
    public ResponseEntity<List<EmployeeRequestDTO>> viewEmployeesByStatus(@PathVariable Status status) throws MalformedURLException {
        List<EmployeeRequestDTO> employeeRequestDTOList = employeeService.getByStatus(status);

//        log.info("Employee list returned by status: {}", status);

        return ResponseEntity.ok(employeeRequestDTOList);
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
    public ResponseEntity<Optional<EmployeeRequestDTO>> viewEmployeesByFullname(@PathVariable String fullname) throws MalformedURLException {
        Optional<EmployeeRequestDTO> employeeDTOList = employeeService.getByFullname(fullname);

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
    public ResponseEntity<List<EmployeeRequestDTO>> viewEmployeesByJobTitle(@PathVariable JobTitle jobTitle) throws MalformedURLException {
        List<EmployeeRequestDTO> employeeRequestDTOList = employeeService.getByJobPosition(jobTitle);

//        log.info("Employee list returned by jobTitle: {}", jobTitle);

        return ResponseEntity.ok(employeeRequestDTOList);
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
    public ResponseEntity<List<EmployeeRequestDTO>> viewEmployeesByEmploymentType(@PathVariable EmploymentType employmentType) throws MalformedURLException {
        List<EmployeeRequestDTO> employeeRequestDTOList = employeeService.getByEmploymentType(employmentType);

//        log.info("Employee list returned by employment type: {}", employmentType);

        return ResponseEntity.ok(employeeRequestDTOList);
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
    public ResponseEntity<List<EmployeeRequestDTO>> viewEmployeesByJoinDate(@PathVariable LocalDate localDate) throws MalformedURLException {
        List<EmployeeRequestDTO> employeeRequestDTOList = employeeService.getByDate(localDate);

//        log.info("Employee list returned by joinDate: {}", localDate);

        return ResponseEntity.ok(employeeRequestDTOList);
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
    public ResponseEntity<EmployeeRequestDTO> updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeRequestDTO employeeRequestDTO) throws IOException {
        employeeService.update(id, employeeRequestDTO);
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
