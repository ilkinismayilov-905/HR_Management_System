package com.example.HR.service;

import com.example.HR.dto.auth.AuthResponseDTO;
import com.example.HR.dto.auth.LoginRequestDTO;
import com.example.HR.dto.auth.RegisterDTO;
import com.example.HR.dto.user.UserResponseDTO;

public interface AuthService {
    public AuthResponseDTO login(LoginRequestDTO request);
    public UserResponseDTO register(RegisterDTO request);
    public AuthResponseDTO refreshToken(String refreshToken);
}
