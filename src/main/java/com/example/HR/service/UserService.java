package com.example.HR.service;


import com.example.HR.dto.UserRequestDTO;
import com.example.HR.dto.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService extends GeneralService<UserRequestDTO,Long>{
    public Optional<UserRequestDTO> getByFullname(String fullname);
    public Optional<UserRequestDTO> getByPassword(String password);
    public Optional<UserRequestDTO> getByEmail(String email);
    public String verifyAccount(String email,String otp);
    public String regenerateOtp(String email);
    public List<UserResponseDTO> findAll();
}
