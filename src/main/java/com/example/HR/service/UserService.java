package com.example.HR.service;


import com.example.HR.dto.UserDTO;
import com.example.HR.enums.UserRoles;

import java.util.Optional;

public interface UserService extends GeneralService<UserDTO,Long>{
    public Optional<UserDTO> getByFullname(String fullname);
    public Optional<UserDTO> getByPassword(String password);
    public Optional<UserDTO> getByEmail(String email);
//    public List<UserDTO> getByRoles(UserRoles role);
    public String verifyAccount(String email,String otp);
    public String regenerateOtp(String email);
    public boolean authenticateUser(String email, String password);
}
