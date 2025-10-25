package com.example.HR.service.implement;

import com.example.HR.converter.UserConverter;
import com.example.HR.dto.auth.AuthResponseDTO;
import com.example.HR.dto.auth.LoginRequestDTO;
import com.example.HR.dto.auth.RegisterDTO;
import com.example.HR.dto.user.UserResponseDTO;
import com.example.HR.entity.RefreshToken;
import com.example.HR.entity.user.User;
import com.example.HR.exception.NotFoundException;
import com.example.HR.repository.RefreshTokenRepository;
import com.example.HR.repository.user.UserRepository;
import com.example.HR.security.JwtUtil;
import com.example.HR.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

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
    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        User user1 = userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        RefreshToken exRefreshToken = refreshTokenRepository.findByUserId(user1.getId());

        String token = jwtUtil.generateToken(user, user.getId(), user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user);

        log.info("User logged in successfully: {}", user.getEmail());
        RefreshToken entityRefreshToken = new RefreshToken();
        entityRefreshToken.setTokenHash(refreshToken);
        entityRefreshToken.setUser(user1);
        entityRefreshToken.setExpiresIn(604800L);

        if(exRefreshToken != null){
            refreshTokenRepository.delete(exRefreshToken);
        }


        RefreshToken savedRefreshToken = refreshTokenRepository.save(entityRefreshToken);

        return AuthResponseDTO.builder()
                .token(token)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(86400L)
                .user(converter.mapToUserInfo(user))
                .build();
    }

    @Override
    public UserResponseDTO register(RegisterDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email artıq istifadə olunub!");
        }

        User user = new User();
        user.setId(request.getId());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullname(request.getFullname());
        user.setRoles(request.getRoles());
        user.setActive(true);

        User savedUser = userRepository.save(user);

//        String token = jwtUtil.generateToken(savedUser, savedUser.getId(), savedUser.getFullname());
//        String refreshToken = jwtUtil.generateRefreshToken(savedUser);

        log.info("New user registered successfully: {}", savedUser.getEmail());

        return converter.entityToResponseDTO(savedUser);

//        return AuthResponseDTO.builder()
//                .token(token)
//                .refreshToken(refreshToken)
//                .expiresIn(86400L)
//                .user(converter.mapToUserInfo(user))
//                .tokenType("Bearer")
//                .build();

    }

    @Override
    public AuthResponseDTO refreshToken(String refreshToken){

        try {

            log.info("Refresh token process started");
            if (refreshToken == null || refreshToken.trim().isEmpty()) {
                throw new RuntimeException("Refresh token boşdur!");
            }

            if (jwtUtil.isTokenExpired(refreshToken)){
                throw new RuntimeException("Refresh token is not validated!");
            }

            RefreshToken entityRefreshToken = refreshTokenRepository.findByTokenHash(refreshToken)
                    .orElseThrow(() -> new NotFoundException("Refresh token is not found"));

            if(entityRefreshToken.getCreatedAt().plusSeconds(entityRefreshToken.getExpiresIn())
                    .isBefore(LocalDateTime.now())){
                refreshTokenRepository.delete(entityRefreshToken);
                throw new RuntimeException("Refresh token is expired");
            }

            User user = entityRefreshToken.getUser();

            if(user == null || !user.isEnabled()){
                throw new NotFoundException("User not found");
            }

            String newToken = jwtUtil.generateToken(user, user.getId(), user.getUsername());

            String newRefreshToken = jwtUtil.generateRefreshToken(user);

            refreshTokenRepository.delete(entityRefreshToken);

            RefreshToken newEntityRefreshToken = new RefreshToken();
            newEntityRefreshToken.setTokenHash(newRefreshToken);
            newEntityRefreshToken.setUser(user);
            newEntityRefreshToken.setExpiresIn(86400L);

            refreshTokenRepository.save(newEntityRefreshToken);

            log.info("Token refresh edildi: {}", user.getEmail());

            return AuthResponseDTO.builder()
                    .token(newToken)
                    .refreshToken(newRefreshToken)
                    .tokenType("Bearer")
                    .expiresIn(604800L)
                    .user(converter.mapToUserInfo(user))
                    .build();
        } catch (Exception e) {
            log.error("Token refresh zamanı xəta: {}", e.getMessage());
            throw new RuntimeException("Token refresh edilə bilmədi: " + e.getMessage());
        }
    }
}