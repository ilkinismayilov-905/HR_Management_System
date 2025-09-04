package com.example.HR.service;

import com.example.HR.dto.auth.AuthResponseDTO;
import com.example.HR.dto.auth.LoginRequestDTO;
import com.example.HR.dto.auth.RegisterDTO;

public interface AuthService {
    public AuthResponseDTO login(LoginRequestDTO request);
    public AuthResponseDTO register(RegisterDTO request);
}
