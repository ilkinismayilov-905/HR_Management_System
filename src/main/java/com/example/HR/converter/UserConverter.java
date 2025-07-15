package com.example.HR.converter;

import com.example.HR.dto.UserDTO;
import com.example.HR.entity.User;
import com.example.HR.entity.employee.Employee;
import com.example.HR.enums.Status;

public class UserConverter extends Convert<UserDTO, User> {
    @Override
    public User dtoToEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setConfirmPassword(dto.getConfirmPassword());
        user.setRoles(dto.getRoles());

        // Note: User relationships should be set in the service layer
        return user;
    }

    @Override
    public UserDTO entityToDto(User entity) {
        if (entity == null){
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
        dto.setConfirmPassword(entity.getConfirmPassword());
        dto.setRoles(entity.getRoles());

        return dto;
    }
}
