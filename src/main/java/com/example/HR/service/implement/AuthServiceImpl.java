package com.example.HR.service.implement;

import com.example.HR.converter.UserConverter;
import com.example.HR.dto.auth.AuthResponseDTO;
import com.example.HR.dto.auth.LoginRequestDTO;
import com.example.HR.dto.auth.RegisterDTO;
import com.example.HR.entity.User;
import com.example.HR.enums.UserRoles;
import com.example.HR.repository.UserRepository;
import com.example.HR.security.JwtUtil;
import com.example.HR.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserConverter converter;


    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = jwtUtil.generateToken(user, user.getId(), user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user);

        log.info("User logged in successfully: {}", user.getEmail());

        return AuthResponseDTO.builder()
                .token(token)
                .refreshToken(refreshToken)
                .expiresIn(86400L)
                .user(converter.mapToUserInfo(user))
                .build();
    }

    @Override
    public AuthResponseDTO register(RegisterDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email artıq istifadə olunub!");
        }

        User user = new User();
        user.setId(request.getId());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullname(request.getFullname());
        user.setRoles(UserRoles.USER);
        user.setActive(true);

        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser, savedUser.getId(), savedUser.getFullname());
        String refreshToken = jwtUtil.generateRefreshToken(savedUser);

        log.info("New user registered successfully: {}", savedUser.getEmail());

        return AuthResponseDTO.builder()
                .token(token)
                .refreshToken(refreshToken)
                .expiresIn(86400L)
                .user(converter.mapToUserInfo(user))
                .build();

    }
}
