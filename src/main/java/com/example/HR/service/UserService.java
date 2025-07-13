package com.example.HR.service;


import com.example.HR.dto.UserDTO;
import com.example.HR.entity.User;

public interface UserService extends GeneralService<UserDTO,Long>{
    public UserDTO getByUsername(String username);
    public UserDTO getByPassword(String password);
    public UserDTO getByEmail(String email);
}
