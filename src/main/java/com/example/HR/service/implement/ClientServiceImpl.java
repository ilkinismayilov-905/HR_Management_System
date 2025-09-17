package com.example.HR.service.implement;

import com.example.HR.dto.client.ClientRequestDTO;
import com.example.HR.dto.client.ClientResponseDTO;
import com.example.HR.enums.ClientStatus;
import com.example.HR.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    @Override
    public ClientResponseDTO save(ClientRequestDTO dto) {

    }

    @Override
    public ClientResponseDTO update(Long id, ClientRequestDTO dto) {
        return null;
    }

    @Override
    public ClientResponseDTO getById(Long id) {
        return null;
    }

    @Override
    public List<ClientResponseDTO> getAll() {
        return List.of();
    }

    @Override
    public List<ClientResponseDTO> getByStatus(ClientStatus status) {
        return List.of();
    }

    @Override
    public Optional<ClientResponseDTO> getByFullname(String fullname) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }
}
