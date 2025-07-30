package com.example.HR.converter;

import com.example.HR.dto.UserDTO;
import com.example.HR.entity.User;
import com.example.HR.entity.employee.Employee;
import com.example.HR.enums.Status;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@NoArgsConstructor
@Component
public class UserConverter extends Convert<UserDTO, User> {
    @Override
    public User dtoToEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setId(dto.getId());
        user.setFullname(dto.getFullname());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
//        user.setRoles(dto.getRoles());

        // Note: User relationships should be set in the service layer
        return user;
    }

    @Override
    public UserDTO entityToDto(User entity) {
        if (entity == null){
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setFullname(entity.getFullname());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
//        dto.setRoles(entity.getRoles());

        return dto;
    }

    /**
     * Updates the given User entity with non-null values from the UserDTO.
     */
    public void update(UserDTO dto, User entity) {
        if (dto == null || entity == null) {
            return;
        }
        if (dto.getId() != null) entity.setId(dto.getId());
        if (dto.getFullname() != null) entity.setFullname(dto.getFullname());
        if (dto.getPassword() != null) entity.setPassword(dto.getPassword());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
//        if (dto.getRoles() != null) entity.setRoles(dto.getRoles());
    }
}
