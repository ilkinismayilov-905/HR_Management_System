package com.example.HR.service;

import com.example.HR.dto.user.UserProfileDTO;
import com.example.HR.dto.user.UserProfileRequest;
import com.example.HR.entity.user.UserAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserProfileService {
    UserProfileDTO updateInfo(UserProfileRequest dto);
    List<UserProfileDTO> findAll();
    UserAttachment uploadAttachment(Long id, MultipartFile file);
}
