package com.example.HR.controller;

import com.example.HR.dto.auth.AuthResponseDTO;
import com.example.HR.dto.auth.LoginRequestDTO;
import com.example.HR.dto.auth.RegisterDTO;
import com.example.HR.service.AuthService;
import com.example.HR.validation.Create;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterDTO request) {
        AuthResponseDTO response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @Operation(summary = "User logout", description = "İstifadəçi çıxışı")
    public ResponseEntity<String> logout() {
        // JWT stateless olduğu üçün sadəcə client-də token silinir
        return ResponseEntity.ok("Logged out successfully");
    }
}
