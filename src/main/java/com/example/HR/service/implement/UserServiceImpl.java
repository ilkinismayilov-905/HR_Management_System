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
    public UserDTO save(UserDTO entity) throws IOException {

        if(entity.getPassword().equals(entity.getConfirmPassword())) {
            User convertedUser = converter.dtoToEntity(entity);

            User savedUser = userRepository.save(convertedUser);

            return converter.entityToDto(savedUser);
        }
        else {
            throw  new IllegalArgumentException("ConfirmPassword did not matched");
        }

    }

    @Override
    public Optional<UserDTO> getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found by username: " + username));


        return Optional.ofNullable(converter.entityToDto(user));
    }

    @Override
    public Optional<UserDTO> getByPassword(String password) {
        User user = userRepository.findByPassword(password)
                .orElseThrow(() -> new NotFoundException("User not found by password: " + password));


        return Optional.ofNullable(converter.entityToDto(user));
    }

    @Override
    public Optional<UserDTO> getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found by email: " + email));

        return Optional.ofNullable(converter.entityToDto(user));
    }

    @Override
    public List<UserDTO> getByRoles(UserRoles role) {
        List<User> user = userRepository.findByRoles(role);

       return converter.entityListToDtoList(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoIDException("There is no user by id: " + id));

        userRepository.delete(user);

    }

    @Override
    public Optional<UserDTO> getById(Long id) {
        if(userRepository.existsById(id)){
            Optional<User> user = userRepository.findById(id);
            return Optional.ofNullable(converter.entityToDto(user.get()));
        }
        throw new NoIDException("There is no user by id: " + id);
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
