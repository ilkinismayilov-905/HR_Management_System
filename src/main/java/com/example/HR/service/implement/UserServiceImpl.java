package com.example.HR.service.implement;

import com.example.HR.converter.UserConverter;
import com.example.HR.dto.user.UserRequestDTO;
import com.example.HR.dto.user.UserResponseDTO;
import com.example.HR.entity.User;
import com.example.HR.exception.NoIDException;
import com.example.HR.exception.NotFoundException;
import com.example.HR.repository.UserRepository;
import com.example.HR.service.UserService;
import com.example.HR.util.EmailUtil;
import com.example.HR.util.OtpUtil;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@Valid
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter converter;
    private final EmailUtil emailUtil;
    private final OtpUtil otpUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter converter, EmailUtil emailUtil, OtpUtil otpUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.converter = converter;
        this.emailUtil = emailUtil;
        this.otpUtil = otpUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserRequestDTO save(UserRequestDTO userRequestDTO) throws IOException {
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(userRequestDTO.getEmail(), otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to sent otp,try again");
        }

        if(userRequestDTO.getPassword().equals(userRequestDTO.getConfirmPassword())) {
            User convertedUser = converter.dtoToEntity(userRequestDTO);
            convertedUser.setOtp(otp);
            convertedUser.setCreatedTime(LocalDateTime.now());
            convertedUser.setPassword(passwordEncoder.encode(convertedUser.getPassword()));

            User savedUser = userRepository.save(convertedUser);

            return converter.entityToDto(savedUser);
        }
        else {
            throw  new IllegalArgumentException("ConfirmPassword did not matched");
        }
//        if(userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()){
//            throw new ValidException("email should be unique");
//        }
//        else {
//
//        }

    }

    @Override
    public Optional<UserResponseDTO> getByFullname(String fullname) {
        User user = userRepository.findByFullname(fullname)
                .orElseThrow(() -> new NotFoundException("User not found by username: " + fullname));


        return Optional.ofNullable(converter.entityToResponseDTO(user));
    }

    @Override
    public Optional<UserResponseDTO> getByPassword(String password) {
        User user = userRepository.findByPassword(password)
                .orElseThrow(() -> new NotFoundException("User not found by password: " + password));


        return Optional.ofNullable(converter.entityToResponseDTO(user));
    }

    @Override
    public Optional<UserResponseDTO> getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found by email: " + email));


        return Optional.ofNullable(converter.entityToResponseDTO(user));
    }

//    @Override
//    public List<UserRequestDTO> getByRoles(UserRoles role) {
//        List<User> user = userRepository.findByRoles(role);
//
//       return converter.entityListToDtoList(user);
//    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoIDException("There is no user by id: " + id));

        userRepository.delete(user);

    }

    @Override
    public UserResponseDTO save(UserResponseDTO dto) throws IOException {
        return null;
    }


    @Override
    public Optional<UserResponseDTO> getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoIDException("There is no user found by id: " + id));

        return Optional.ofNullable(converter.entityToResponseDTO(user));

    }

    @Override
    public UserResponseDTO update(Long id, UserResponseDTO updatedDto) {
        return null;
    }

    @Override
    public UserResponseDTO update(Long id, UserRequestDTO updatedDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found by id: " + id));

        // Update only non-null fields from updatedDto
//        if (updatedDto.getUsername() != null) user.setUsername(updatedDto.getUsername());
//        if (updatedDto.getPassword() != null) user.setPassword(updatedDto.getPassword());
//        if (updatedDto.getEmail() != null) user.setEmail(updatedDto.getEmail());
//        if (updatedDto.getConfirmPassword() != null) user.setConfirmPassword(updatedDto.getConfirmPassword());
//        if (updatedDto.getRoles() != null) user.setRoles(updatedDto.getRoles());

        converter.update(updatedDto,user);

        User savedUser = userRepository.save(user);
        return converter.entityToResponseDTO(savedUser);
    }

    @Override
    public List<UserResponseDTO> getAll() throws MalformedURLException {
        return List.of();
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return converter.entityListToResponseDTOList(userRepository.findAll());
    }

    @Override
    public String verifyAccount(String email,String otp){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found by email: " + email));

        if(user.getOtp().equals(otp) && Duration.between(user.getCreatedTime(),
                LocalDateTime.now()).getSeconds() < (1*60)) {
            user.setActive(true);
            userRepository.save(user);
            return "OTP_SUCCESS";
        }

        return "OTP_INVALID";
    }

    @Override
    public String regenerateOtp(String email){

        String otp = otpUtil.generateOtp();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found by email: " + email));

        try{
            emailUtil.sendOtpEmail(email,otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to sent OTP");
        }

        user.setOtp(otp);
        user.setCreatedTime(LocalDateTime.now());
        userRepository.save(user);

        return "OTP_SUCCES";

    }

//    @Override
//    public String login(UserRequestDTO userDTO){
//        User user = userRepository.findByFullname(userDTO.getFullname())
//                .orElseThrow(() -> new NotFoundException("User not found by username: " + userDTO.getFullname()));
//
//        if(userDTO.getPassword().equals(user.getPassword()));
//    }

}
