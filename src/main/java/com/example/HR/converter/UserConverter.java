package com.example.HR.converter;

import com.example.HR.dto.auth.UserInfoDTO;
import com.example.HR.dto.user.UserProfileDTO;
import com.example.HR.dto.user.UserProfileRequest;
import com.example.HR.dto.user.UserRequestDTO;
import com.example.HR.dto.user.UserResponseDTO;
import com.example.HR.entity.user.User;
import com.example.HR.entity.user.UserProfile;
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
        dto.setActive(user.isEnabled());
        return dto;
    }

    public List<UserResponseDTO> entityListToResponseDTOList(List<User> users) {
        return users.stream()
                .map(this::entityToResponseDTO)
                .collect(Collectors.toList());
    }

    public UserInfoDTO mapToUserInfo(User user) {
        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setId(user.getId());
        userInfo.setEmail(user.getEmail());
        userInfo.setFullname(user.getFullname());
        userInfo.setRole(user.getRoles().name());
        userInfo.setActive(user.isEnabled());
        return userInfo;
    }

    public UserProfileDTO toUserInformationDTO(UserProfile user) {
        return UserProfileDTO.builder()
                .id(user.getUser().getId())
                .email(user.getEmail())
                .address(user.getAddress())
                .city(user.getCity())
                .country(user.getCountry())
                .fullname(user.getFullname())
                .state(user.getState())
                .phoneNumber(String.valueOf(user.getPhoneNumber()))
                .postalCode(user.getPostalCode())
                .build();
    }

    public List<UserProfileDTO> toUserInformationDTOList(List<UserProfile> users) {
        return users.stream()
                .map(this::toUserInformationDTO)
                .collect(Collectors.toList());
    }

    public UserProfile toUserInformation(UserProfileRequest request) {
        return UserProfile.builder()
                .email(request.getEmail())
                .address(request.getAddress())
                .city(request.getCity())
                .country(request.getCountry())
                .fullname(request.getFullname())
                .state(request.getState())
                .phoneNumber(request.getPhoneNumber())
                .postalCode(request.getPostalCode())
                .build();
    }

    public void updateInfo(UserProfileRequest dto, UserProfile user) {
        if (dto == null || user == null) return;
        if (dto.getFullname() != null) user.setFullname(dto.getFullname());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getAddress() != null) user.setAddress(dto.getAddress());
        if (dto.getCity() != null) user.setCity(dto.getCity());
        if (dto.getCountry() != null) user.setCountry(dto.getCountry());
        if (dto.getPhoneNumber() != null) user.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getPostalCode() != null) user.setPostalCode(dto.getPostalCode());
        if (dto.getState() != null) user.setState(dto.getState());
    }
}
