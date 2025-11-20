package com.example.HR.converter.user;

import com.example.HR.dto.employee.EmployeeAttachmentDTO;
import com.example.HR.dto.user.UserAttachmentDTO;
import com.example.HR.dto.user.UserProfileRequestDTO;
import com.example.HR.dto.user.UserProfileResponseDTO;
import com.example.HR.entity.employee.Employee;
import com.example.HR.entity.employee.EmployeeAttachment;
import com.example.HR.entity.user.User;
import com.example.HR.entity.user.UserAttachment;
import com.example.HR.entity.user.UserProfile;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserProfileConverter {

    public void updateInfo(UserProfileRequestDTO requestDTO, UserProfile profile) {
        if (requestDTO == null || profile == null) return;
        profile.setAddress(requestDTO.getAddress());
        profile.setCity(requestDTO.getCity());
        profile.setState(requestDTO.getState());
        profile.setCountry(requestDTO.getCountry());
        profile.setPostalCode(requestDTO.getPostalCode());
    }

//    public List<UserProfileResponseDTO> toResponseDTOList(List<UserProfile> profiles) {
//        return profiles.stream()
//                .map(profile -> {
//                    User user = profile.getUser();
//                    Employee employee = user.getEmployee();
//                    return toResponseDTO(user, profile, employee);
//                })
//                .collect(Collectors.toList());
//    }
public UserProfileResponseDTO toResponseDTO(User user, UserProfile profile, Employee phoneNumber) {
    return UserProfileResponseDTO.builder()
            .userId(user.getId())
            .fullname(user.getFullname())
            .email(user.getEmail())
            .phoneNumber(phoneNumber.getPhoneNumber())
            .address(profile != null ? profile.getAddress() : null)
            .city(profile != null ? profile.getCity() : null)
            .state(profile != null ? profile.getState() : null)
            .country(profile != null ? profile.getCountry() : null)
            .postalCode(profile != null ? profile.getPostalCode() : null)
            .updatedAt(user.getUpdatedAt())
            .attachments(profile != null && profile.getAttachments() != null
                    ? profile.getAttachments().stream()
                    .map(this::mapAttachmentToDTO)
                    .collect(Collectors.toList())
                    : Collections.emptyList())
            .build();
}

public UserProfileResponseDTO toResponseGet(UserProfile profile) {
    return UserProfileResponseDTO.builder()
            .address(profile != null ? profile.getAddress() : null)
            .city(profile != null ? profile.getCity() : null)
            .state(profile != null ? profile.getState() : null)
            .country(profile != null ? profile.getCountry() : null)
            .postalCode(profile != null ? profile.getPostalCode() : null)
            .attachments(profile != null && profile.getAttachments() != null
                    ? profile.getAttachments().stream()
                    .map(this::mapAttachmentToDTO)
                    .collect(Collectors.toList())
                    : Collections.emptyList())
            .build();
}


        private UserAttachmentDTO mapAttachmentToDTO(UserAttachment attachment) {
        return UserAttachmentDTO.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .originalFileName(attachment.getOriginalFileName())
                .contentType(attachment.getContentType())
                .fileSize(attachment.getFileSize())
                .uploadedDate(attachment.getUploadedDate())
                .build();
    }

}
