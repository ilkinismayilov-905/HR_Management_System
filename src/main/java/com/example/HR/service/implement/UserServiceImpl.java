package com.example.HR.service.implement;

import com.example.HR.converter.EmployeeConverter;
import com.example.HR.converter.UserConverter;
import com.example.HR.dto.UserDTO;
import com.example.HR.entity.User;
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
    private final UserConverter converter = new UserConverter();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()){
            throw new NotFoundException("User not found by username: " + username);
        }

        return Optional.ofNullable(converter.entityToDto(user.get()));
    }

    @Override
    public Optional<UserDTO> getByPassword(String password) {
        Optional<User> user = userRepository.findByPassword(password);

        if (user.isEmpty()){
            throw new NotFoundException("User not found by password: " + password);
        }

        return Optional.ofNullable(converter.entityToDto(user.get()));
    }

    @Override
    public Optional<UserDTO> getByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()){
            throw new NotFoundException("User not found by email: " + email);
        }

        return Optional.ofNullable(converter.entityToDto(user.get()));
    }

    @Override
    public void deleteById(Long id) {
       if(userRepository.existsById(id)){
           userRepository.deleteById(id);
       }
       throw new NoIDException("There is no user by id: " + id);

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
    public List<UserDTO> getAll() throws MalformedURLException {
        return converter.entityListToDtoList(userRepository.findAll());
    }
}
