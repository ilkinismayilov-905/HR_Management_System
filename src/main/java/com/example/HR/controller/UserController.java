package com.example.HR.controller;

import com.example.HR.dto.EmployeeDTO;
import com.example.HR.dto.UserDTO;
import com.example.HR.entity.User;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import com.example.HR.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    //ADD NEW USER
    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with the provided information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/add")
    public ResponseEntity<UserDTO> createEmployee(@RequestBody @Valid UserDTO UserDTO) throws IOException {
//        log.info("Creating new employee: {}", employeeDTO.getFullname());
        UserDTO savedUser = userService.save(UserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserDTO);
    }

    //GET ALL USERS

    @Operation(summary = "Get all users",
            description = "Returns a list of all users"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No users found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDTO>> viewAllEmployees() throws MalformedURLException {
        List<UserDTO> userDTOList = userService.getAll();
//        log.info("All Employee list returned");

        return ResponseEntity.ok(userDTOList);
    }

    //GET USER BY ID

    @Operation(summary = "Get user by ID",
            description = "Returns a single user by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getEmployeeById(@PathVariable Long id) {
        return userService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //GET USER  BY EMAIL

    @Operation(summary = "Get user by email",
            description = "Returns a list of all user by email"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of user retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No user found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<Optional<UserDTO>> viewEmployeesByEmail(@PathVariable String email) throws MalformedURLException {
        Optional<UserDTO> userDTOList = userService.getByEmail(email);

//        log.info("Employee list returned by status: {}", status);

        return ResponseEntity.ok(userDTOList);
    }

    //GET USER BY ROLE

    @Operation(summary = "Get user by username",
            description = "Returns a list of all user by username"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No user found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getByRole/{role}")
    public ResponseEntity<User> viewEmployeesByUsername(@PathVariable String username) throws MalformedURLException {
        Optional<UserDTO> user = userService.getByUsername(username);
        User convertedUser =

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
        // Note: You'll need to implement update method in service
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
