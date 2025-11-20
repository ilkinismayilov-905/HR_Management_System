package com.example.HR.service.implement;

import com.example.HR.converter.user.UserConverter;
import com.example.HR.converter.user.UserProfileConverter;
import com.example.HR.dto.user.UserProfileResponseDTO;
import com.example.HR.dto.user.UserProfileRequestDTO;
import com.example.HR.entity.employee.Employee;
import com.example.HR.entity.user.User;
import com.example.HR.entity.user.UserAttachment;
import com.example.HR.entity.user.UserProfile;
import com.example.HR.exception.NotFoundException;
import com.example.HR.exception.ResourceNotFoundException;
import com.example.HR.repository.employee.EmployeeRepository;
import com.example.HR.repository.user.UserProfileRepository;
import com.example.HR.repository.user.UserRepository;
import com.example.HR.service.UserProfileService;
import com.example.HR.service.implement.fileStorage.UserImagesService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileConverter converter;
    private final UserRepository userRepository;
    private final UserProfileRepository profileRepository;
    private final UserImagesService imageService;
    private final EmployeeRepository employeeRepository;

    @Override
    public UserProfileResponseDTO updateProfile(UserProfileRequestDTO requestDTO, String authenticatedUserEmail) {
        log.info("Updating profile for user: {}", authenticatedUserEmail);


        User user = userRepository.findByEmail(authenticatedUserEmail)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + authenticatedUserEmail));

        if (requestDTO.getFullname() != null) {
            user.setFullname(requestDTO.getFullname());
        }
        if (requestDTO.getEmail() != null) {
            user.setEmail(requestDTO.getEmail());
        }



        Employee employee = employeeRepository.findByFullnameFullname(user.getFullname())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found for user: " + user.getFullname()));

        if (requestDTO.getPhoneNumber() != null) {
            employee.setPhoneNumber(requestDTO.getPhoneNumber());
        }



        UserProfile profile = profileRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    log.info("Creating new profile for user: {}", user.getId());
                    return UserProfile.builder()
                            .user(user)
                            .build();
                });


        converter.updateInfo(requestDTO, profile);



        User savedUser = userRepository.save(user);
        Employee savedEmployee = employeeRepository.save(employee);
        UserProfile savedProfile = profileRepository.save(profile);


        log.info("Successfully updated profile for user: {}", savedUser.getId());

        return converter.toResponseDTO(savedUser, savedProfile, savedEmployee);

    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponseDTO getMyProfile(String authenticatedUserEmail) {
        log.info("Fetching profile for user: {}", authenticatedUserEmail);

        User user = userRepository.findByEmail(authenticatedUserEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + authenticatedUserEmail));

        UserProfile profile = profileRepository.findByUserId(user.getId()).orElse(null);
        Employee employee = employeeRepository.findByFullnameFullname(user.getFullname()).orElse(null);

        return converter.toResponseDTO(user, profile, employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProfileResponseDTO> findAll() {
        log.info("Fetching all user profiles");

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    UserProfile profile = profileRepository.findByUserId(user.getId()).orElse(null);
                    Employee employee = employeeRepository.findByFullnameFullname(user.getFullname())
                            .orElseThrow(() -> new EntityNotFoundException("Employee not found for user: " + user.getFullname()));

                    return converter.toResponseDTO(user, profile,employee);
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserAttachment uploadAttachment(Long id, MultipartFile file) {
        UserProfile profile = profileRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile not found with ID: " + id ));
        log.info("Employee found by employeeID: {}", id);

        UserAttachment attachment = imageService.storeFile(file,profile);
        log.info("Uploaded attachment {} for ticket {}", file.getOriginalFilename(), id);


        return attachment;
    }

    @Override
    public UserProfileResponseDTO findById(Long id,String authenticatedUserEmail) {

        User user = userRepository.findByEmail(authenticatedUserEmail)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + authenticatedUserEmail));

        UserProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile not found with ID: " + id ));

       return converter.toResponseGet(profile);
    }

}
