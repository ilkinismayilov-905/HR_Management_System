package com.example.HR.controller;

import com.example.HR.dto.auth.AuthResponseDTO;
import com.example.HR.dto.auth.LoginRequestDTO;
import com.example.HR.dto.auth.RefreshTokenRequestDTO;
import com.example.HR.dto.auth.RegisterDTO;
import com.example.HR.dto.user.UserResponseDTO;
import com.example.HR.service.AuthService;
import com.example.HR.validation.Create;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class AutehticationController {
    private final AuthService authService ;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Validated(Create.class) @RequestBody LoginRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "User registration", description = "Yeni employee qeydiyyatı")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterDTO request) {
        UserResponseDTO response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @Operation(summary = "User logout", description = "İstifadəçi çıxışı")
    public ResponseEntity<String> logout() {
        // JWT stateless olduğu üçün sadəcə client-də token silinir
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDTO> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO requestDTO){

        try {
            log.info("Refresh token request received");
            AuthResponseDTO responseDTO = authService.refreshToken(requestDTO.getRefreshToken());
            log.info("Refresh token successful");

            return ResponseEntity.ok(responseDTO);

        }catch (Exception e) {
            log.error("Refresh token failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(AuthResponseDTO.builder()
                            .build());
        }
    }
}
