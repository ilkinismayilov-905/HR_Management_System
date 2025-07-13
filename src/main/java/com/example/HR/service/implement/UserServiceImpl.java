package com.example.HR.service.implement;

import com.example.HR.dto.UserDTO;
import com.example.HR.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Override
    public UserDTO getByUsername(String username) {
        return null;
    }

    @Override
    public UserDTO getByPassword(String password) {
        return null;
    }

    @Override
    public UserDTO getByEmail(String email) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public UserDTO save(UserDTO entity) throws IOException {
        return null;
    }

    @Override
    public Optional<UserDTO> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<UserDTO> getAll() throws MalformedURLException {
        return List.of();
    }
}
