package com.example.HR.service.implement;

import com.example.HR.converter.EmployeeConverter;
import com.example.HR.converter.UserConverter;
import com.example.HR.dto.UserDTO;
import com.example.HR.entity.User;
import com.example.HR.enums.UserRoles;
import com.example.HR.exception.NoIDException;
import com.example.HR.exception.NotFoundException;
import com.example.HR.repository.UserRepository;
import com.example.HR.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter converter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @Override
    public UserDTO save(UserDTO userDTO) throws IOException {

        if(userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            User convertedUser = converter.dtoToEntity(userDTO);

            User savedUser = userRepository.save(convertedUser);

            return converter.entityToDto(savedUser);
        }
        else {
            throw  new IllegalArgumentException("ConfirmPassword did not matched");
        }

    }

    @Override
    public Optional<UserDTO> getByFullname(String fullname) {
        User user = userRepository.findByFullname(fullname)
                .orElseThrow(() -> new NotFoundException("User not found by username: " + fullname));


        return Optional.ofNullable(converter.entityToDto(user));
    }

    @Override
    public Optional<UserDTO> getByPassword(String password) {
        User user = userRepository.findByPassword(password)
                .orElseThrow(() -> new NotFoundException("User not found by password: " + password));


        return Optional.ofNullable(converter.entityToDto(user));
    }

    @Override
    public List<UserDTO> getByEmail(String email) {
        List<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()){
            throw new NotFoundException("There is no user by email: " + email);
        }

        return converter.entityListToDtoList(user);
    }

//    @Override
//    public List<UserDTO> getByRoles(UserRoles role) {
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
    public Optional<UserDTO> getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoIDException("There is no user found by id: " + id));

        return Optional.ofNullable(converter.entityToDto(user));

    }

    @Override
    public UserDTO update(Long id, UserDTO updatedDto) {
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
        return converter.entityToDto(savedUser);
    }

    @Override
    public List<UserDTO> getAll() throws MalformedURLException {
        return converter.entityListToDtoList(userRepository.findAll());
    }

}
