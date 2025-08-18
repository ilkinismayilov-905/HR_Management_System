package com.example.HR.service;


import com.example.HR.dto.user.UserRequestDTO;
import com.example.HR.dto.user.UserResponseDTO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService extends GeneralService<UserResponseDTO,Long>{
    public Optional<UserResponseDTO> getByFullname(String fullname);
    public Optional<UserResponseDTO> getByPassword(String password);
    public Optional<UserResponseDTO> getByEmail(String email);
    public String verifyAccount(String email,String otp);
    public String regenerateOtp(String email);
    public List<UserResponseDTO> findAll();
    public UserResponseDTO update(Long id, UserRequestDTO updatedDto);
    public UserRequestDTO save(UserRequestDTO userRequestDTO) throws IOException;

}
