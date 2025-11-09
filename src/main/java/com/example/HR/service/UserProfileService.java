package com.example.HR.service;

import com.example.HR.dto.user.UserProfileResponseDTO;
import com.example.HR.dto.user.UserProfileRequestDTO;
import com.example.HR.entity.user.UserAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserProfileService {
    UserProfileResponseDTO updateProfile(UserProfileRequestDTO requestDTO, String authenticatedUserEmail);

    /**
     * Get authenticated user's profile
     */
    UserProfileResponseDTO getMyProfile(String authenticatedUserEmail);
    List<UserProfileResponseDTO> findAll();
    UserAttachment uploadAttachment(Long id, MultipartFile file);
}
