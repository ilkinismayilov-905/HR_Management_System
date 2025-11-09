package com.example.HR.controller;

import com.example.HR.converter.user.UserConverter;
import com.example.HR.dto.user.UserRequestDTO;
import com.example.HR.dto.user.UserResponseDTO;
import com.example.HR.service.PasswordResetTokenService;
import com.example.HR.service.UserService;
import com.example.HR.util.EmailUtil;
import com.example.HR.util.OtpUtil;
import com.example.HR.validation.Create;
import com.example.HR.validation.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final PasswordResetTokenService resetTokenService;

    @Autowired
    public UserController(UserService userService, OtpUtil otpUtil, EmailUtil emailUtil, UserConverter userConverter, PasswordResetTokenService resetTokenService) {
        this.userService = userService;
        this.otpUtil = otpUtil;
        this.emailUtil = emailUtil;
        this.userConverter = userConverter;
        this.resetTokenService = resetTokenService;
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
    public ResponseEntity<UserRequestDTO> createUser(@RequestBody @Validated(Create.class) UserRequestDTO UserRequestDTO) throws IOException {
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
    public ResponseEntity<Map<String,Object>> getUserById(@PathVariable Long id) {
       log.info("REST request to get User by id : {}", id);
       Map<String,Object> response = new HashMap<>();

       try {
           UserResponseDTO dto = userService.getById(id);

           response.put("user",dto);
           response.put("success",true);

           return ResponseEntity.status(HttpStatus.OK).body(response);
       }catch (IllegalArgumentException e){
           response.put("success", false);
           response.put("message", e.getMessage());
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
       }
       catch (Exception e) {

           response.put("success", false);
           response.put("message", e.getMessage());

           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
       }
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
    public ResponseEntity<Optional<UserResponseDTO>> viewUsersByEmail(@PathVariable String email) throws MalformedURLException {
        Optional<UserResponseDTO> userDTOList = userService.getByEmail(email);

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
    public ResponseEntity<Map<String,Object>> updateUser(@PathVariable Long id,
                                                         @RequestBody @Validated(Update.class)
                                                         UserRequestDTO userRequestDTO) throws IOException {
        log.info("REST request to update user : {} ", id);
        Map<String,Object> response = new HashMap<>();

        try {
            UserResponseDTO update = userService.update(id, userRequestDTO);
            response.put("success",true);
            response.put("user",update);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (IllegalArgumentException e){
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        catch (Exception e) {

            response.put("success", false);
            response.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
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

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) throws MessagingException {
        resetTokenService.createPasswordResetToken(email);
        return ResponseEntity.ok("Əgər email mövcuddursa, reset link göndərildi.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token,
                                                @RequestParam String newPassword){
        boolean succes = resetTokenService.resetPassword(token,newPassword);

        return succes
                ? ResponseEntity.ok("Şifrə uğurla yeniləndi.")
                : ResponseEntity.badRequest().body("Token etibarsız və ya vaxtı bitib.");
    }



}
