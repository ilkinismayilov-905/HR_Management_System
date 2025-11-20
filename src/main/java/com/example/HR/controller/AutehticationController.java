package com.example.HR.controller;

import com.example.HR.dto.auth.AuthResponseDTO;
import com.example.HR.dto.auth.LoginRequestDTO;
import com.example.HR.dto.auth.RefreshTokenRequestDTO;
import com.example.HR.dto.auth.RegisterDTO;
import com.example.HR.dto.user.UserResponseDTO;
import com.example.HR.security.JwtUtil;
import com.example.HR.service.AuthService;
import com.example.HR.validation.Create;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class AutehticationController {
    private final AuthService authService ;
    private final JwtUtil jwtUtil;

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

    @GetMapping("/get")
    public Map<String, Object> getCurrentUser(Authentication authentication, HttpServletRequest request) {
        // Authorization header-i yoxlayaq
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Map.of("error", "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        Date expiration = jwtUtil.extractExpiration(token);
        long expiresIn = (expiration.getTime() - System.currentTimeMillis()) / 1000;

        // Rolları müəyyənləşdiririk
        List<String> roles = new ArrayList<>();
        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails userDetails) {
                for (GrantedAuthority authority : userDetails.getAuthorities()) {
                    roles.add(authority.getAuthority());
                }
            } else if (principal instanceof com.example.HR.entity.user.User user) {
                // Əgər sənin entity `User` sinfin `UserDetails` implement edibsə:
                user.getAuthorities().forEach(a -> roles.add(a.getAuthority()));
            }
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("username", username);
        response.put("roles", roles);
        response.put("expiresIn", Math.max(expiresIn, 0));

        return response;
    }
}
