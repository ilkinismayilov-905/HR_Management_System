package com.example.HR.controller;

import com.example.HR.dto.EmployeeDTO;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import com.example.HR.service.implement.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
//@RequiredArgsConstructor
@Validated
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeServiceImpl employeeServiceImpl;

//    @Autowired
//    public EmployeeController(EmployeeServiceImpl employeeServiceImpl) {
//        this.employeeServiceImpl = employeeServiceImpl;
//    }

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeServiceImpl) {
        this.employeeServiceImpl = employeeServiceImpl;
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
    public ResponseEntity<Void> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) throws IOException {
//        log.info("Creating new employee: {}", employeeDTO.getFullname());
        employeeServiceImpl.save(employeeDTO);

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
        List<EmployeeDTO> employeeDTOList = employeeServiceImpl.getAll();
//        log.info("All Employee list returned");

        return ResponseEntity.ok(employeeDTOList);
    }


    @Operation(summary = "Get employees by status",
            description = "Returns a list of all employees by status"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No employees found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getByStatus")
    public ResponseEntity<List<EmployeeDTO>> viewEmployeesByStatus(@PathVariable Status status) throws MalformedURLException {
        List<EmployeeDTO> employeeDTOList = employeeServiceImpl.getByStatus(status);

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
        List<EmployeeDTO> employeeDTOList = employeeServiceImpl.getByFulName(fullname);

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
    @GetMapping("/getByJob")
    public ResponseEntity<List<EmployeeDTO>> viewEmployeesByJobTitle(@PathVariable JobTitle jobTitle) throws MalformedURLException {
        List<EmployeeDTO> employeeDTOList = employeeServiceImpl.getByJobPosition(jobTitle);

//        log.info("Employee list returned by jobTitle: {}", jobTitle);

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
    @GetMapping("/getByDate")
    public ResponseEntity<List<EmployeeDTO>> viewEmployeesByJoinDate(@PathVariable LocalDate localDate) throws MalformedURLException {
        List<EmployeeDTO> employeeDTOList = employeeServiceImpl.getByDate(localDate);

//        log.info("Employee list returned by joinDate: {}", localDate);

        return ResponseEntity.ok(employeeDTOList);
    }
}
