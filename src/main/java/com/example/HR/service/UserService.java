package com.example.HR.service;


import com.example.HR.dto.UserDTO;
import com.example.HR.entity.User;

import java.util.Optional;

public interface UserService extends GeneralService<UserDTO,Long>{
    public Optional<UserDTO> getByUsername(String username);
    public Optional<UserDTO> getByPassword(String password);
    public Optional<UserDTO> getByEmail(String email);
}
