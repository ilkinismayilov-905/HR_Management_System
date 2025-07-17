package com.example.HR.service;


import com.example.HR.dto.UserDTO;
import com.example.HR.enums.UserRoles;

import java.util.List;
import java.util.Optional;

public interface UserService extends GeneralService<UserDTO,Long>{
    public Optional<UserDTO> getByUsername(String username);
    public Optional<UserDTO> getByPassword(String password);
    public Optional<UserDTO> getByEmail(String email);
    public List<UserDTO> getByRoles(UserRoles role);
}
