package com.example.HR.controller;

import com.example.HR.dto.EmployeeDTO;
import com.example.HR.dto.UserDTO;
import com.example.HR.entity.User;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import com.example.HR.enums.UserRoles;
import com.example.HR.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
//@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    //ADD NEW USER
    @Operation(
            summary = "Register a new user",
            description = "Register a new user with the provided information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO UserDTO) throws IOException {
//        log.info("Creating new user: {}", UserDTO.getUsername());
        UserDTO savedUser = userService.save(UserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
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
    public ResponseEntity<List<UserDTO>> viewAllUsers() throws MalformedURLException {
        List<UserDTO> userDTOList = userService.getAll();
//        log.info("All user list returned");

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
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
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
    public ResponseEntity<Optional<UserDTO>> viewUsersByEmail(@PathVariable String email) throws MalformedURLException {
        Optional<UserDTO> userDTOList = userService.getByEmail(email);

//        log.info("User list returned by email: {}", email);

        return ResponseEntity.ok(userDTOList);
    }

//    //GET USER BY ROLE
//
//    @Operation(summary = "Get user by role",
//            description = "Returns a list of all user by role"
//    )
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
//            @ApiResponse(responseCode = "404", description = "No user found"),
//            @ApiResponse(responseCode = "500", description = "Internal server error")
//    })
//    @GetMapping("/getByRole/{role}")
//    public ResponseEntity<List<UserDTO>> viewUsersByRole(@PathVariable UserRoles role) throws MalformedURLException {
//        List<UserDTO> user = userService.getByRoles(role);
//
////        log.info("User list returned by role: {}" ,role);
//        return ResponseEntity.ok(user);
//    }

    //UPDATE USER

    @Operation(summary = "Update user",
            description = "Updates an existing user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) throws IOException {
        userService.update(id,userDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete user",
            description = "Deletes an user by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Verify OTP",
            description = "Verifies current OTP with given"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP deleted successfully"),
            @ApiResponse(responseCode = "404", description = "OTP not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/verify-otp")
    public ResponseEntity<String> verifyAccount(@RequestParam String email,
                                                @RequestParam String otp){
        userService.verifyAccount(email,otp);

        return ResponseEntity.ok().build();
    }

//    @PutMapping("/regenerate-otp")
//    public ResponseEntity<String> regenerateOtp(@RequestParam String email){
//
//    }



}
