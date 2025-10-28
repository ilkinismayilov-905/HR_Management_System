package com.example.HR.service.implement;

import com.example.HR.converter.UserConverter;
import com.example.HR.dto.user.UserProfileDTO;
import com.example.HR.dto.user.UserProfileRequest;
import com.example.HR.entity.employee.EmployeeAttachment;
import com.example.HR.entity.user.User;
import com.example.HR.entity.user.UserAttachment;
import com.example.HR.entity.user.UserProfile;
import com.example.HR.exception.ResourceNotFoundException;
import com.example.HR.repository.user.UserProfileRepository;
import com.example.HR.repository.user.UserRepository;
import com.example.HR.service.UserProfileService;
import com.example.HR.service.implement.fileStorage.UserImagesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserConverter converter;
    private final UserRepository userRepository;
    private final UserProfileRepository profileRepository;
    private final UserImagesService imageService;

    @Override
    public UserProfileDTO updateInfo(UserProfileRequest dto) {
        log.info("update information");
        Long id = getCurrentUser().getId();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile entity = profileRepository.findById(id)
                        .orElse(new UserProfile());
        converter.updateInfo(dto,entity);
        entity.setId(id);
        entity.setUser(user);
        UserProfile saved = profileRepository.save(entity);

        return converter.toUserInformationDTO(saved);

    }

    @Override
    public List<UserProfileDTO> findAll() {
        log.info("find all user information");

        List<UserProfile> list = profileRepository.findAll();

        return  converter.toUserInformationDTOList(list);
    }

    @Override
    public UserAttachment uploadAttachment(Long id, MultipartFile file) {
        UserProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile not found with ID: " + id ));
        log.info("Employee found by employeeID: {}", id);

        UserAttachment attachment = imageService.storeFile(file,profile);
        log.info("Uploaded attachment {} for ticket {}", file.getOriginalFilename(), id);

        return attachment;
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
