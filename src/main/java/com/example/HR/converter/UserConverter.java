package com.example.HR.converter;

import com.example.HR.dto.user.UserRequestDTO;
import com.example.HR.dto.user.UserResponseDTO;
import com.example.HR.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor
@Component
public class UserConverter extends Convert<UserRequestDTO, User> {
    @Override
    public User dtoToEntity(UserRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setId(dto.getId());
        user.setFullname(dto.getFullname());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setRoles(dto.getRoles());

        // Note: User relationships should be set in the service layer
        return user;
    }

    @Override
    public UserRequestDTO entityToDto(User entity) {
        if (entity == null){
            return null;
        }

        UserRequestDTO dto = new UserRequestDTO();
        dto.setId(entity.getId());
        dto.setFullname(entity.getFullname());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
        dto.setRoles(entity.getRoles());

        return dto;
    }

    /**
     * Updates the given User entity with non-null values from the UserRequestDTO.
     */
    public void update(UserRequestDTO dto, User entity) {
        if (dto == null || entity == null) {
            return;
        }
        if (dto.getId() != null) entity.setId(dto.getId());
        if (dto.getFullname() != null) entity.setFullname(dto.getFullname());
        if (dto.getPassword() != null) entity.setPassword(dto.getPassword());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getRoles() != null) entity.setRoles(dto.getRoles());
    }

    public UserResponseDTO entityToResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setEmail(user.getEmail());
        dto.setFullname(user.getFullname());
        dto.setRoles(user.getRoles());
        dto.setActive(user.isActive());
        return dto;
    }

    public List<UserResponseDTO> entityListToResponseDTOList(List<User> users) {
        return users.stream()
                .map(this::entityToResponseDTO)
                .collect(Collectors.toList());
    }
}
