package com.example.HR.controller;

import com.example.HR.converter.UserConverter;
import com.example.HR.dto.EmployeeRequestDTO;
import com.example.HR.dto.UserRequestDTO;
import com.example.HR.dto.UserResponseDTO;
import com.example.HR.service.UserService;
import com.example.HR.util.EmailUtil;
import com.example.HR.util.OtpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@Slf4j
//@RequiredArgsConstructor
@Valid
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final OtpUtil otpUtil;
    private final EmailUtil emailUtil;
    private final UserConverter userConverter;

    @Autowired
    public UserController(UserService userService, OtpUtil otpUtil, EmailUtil emailUtil, UserConverter userConverter) {
        this.userService = userService;
        this.otpUtil = otpUtil;
        this.emailUtil = emailUtil;
        this.userConverter = userConverter;
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
    public ResponseEntity<UserRequestDTO> createUser(@RequestBody @Valid UserRequestDTO UserRequestDTO) throws IOException {
//        log.info("Creating new user: {}", UserRequestDTO.getUsername());
        UserRequestDTO savedUser = userService.save(UserRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
//        boolean authenticated = userService.authenticateUser(email, password);
//        if (authenticated) {
//            return ResponseEntity.ok("Login successful");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//    }

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
    public ResponseEntity<List<UserResponseDTO>> viewAllUsers() throws MalformedURLException {
        List<UserResponseDTO> userDTOList = userService.findAll();
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
    public ResponseEntity<UserRequestDTO> getUserById(@PathVariable Long id) {
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
    public ResponseEntity<Optional<UserRequestDTO>> viewUsersByEmail(@PathVariable String email) throws MalformedURLException {
        Optional<UserRequestDTO> userDTOList = userService.getByEmail(email);

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
//    public ResponseEntity<List<UserRequestDTO>> viewUsersByRole(@PathVariable UserRoles role) throws MalformedURLException {
//        List<UserRequestDTO> user = userService.getByRoles(role);
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
    public ResponseEntity<EmployeeRequestDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestDTO userRequestDTO) throws IOException {
        userService.update(id, userRequestDTO);
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
        String result = userService.verifyAccount(email,otp);

        if ("OTP_SUCCESS".equals(result)) {
            return ResponseEntity.ok("OTP verified successfully");
        } else if ("OTP_INVALID".equals(result)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please regenerate OTP");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error");
        }
    }

    @PutMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email){
        String result = userService.regenerateOtp(email);
        if ("OTP_SUCCES".equals(result)) {

            return ResponseEntity.ok("OTP regenerated successfully");

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Can not regenerate OTP");
        }

    }

    @PostMapping("/send")
    public ResponseEntity<?> sendOtp(@RequestParam String email) throws MessagingException {
        String otp = otpUtil.generateOtp(); // 6 rəqəmli OTP yarat
        emailUtil.sendOtpEmail(email, otp);
        return ResponseEntity.ok("OTP email göndərildi");
    }



}
