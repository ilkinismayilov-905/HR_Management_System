package com.example.HR.service.implement;

import com.example.HR.converter.ClientConverter;
import com.example.HR.dto.client.ClientRequestDTO;
import com.example.HR.dto.client.ClientResponseDTO;
import com.example.HR.entity.client.Client;
import com.example.HR.enums.ClientStatus;
import com.example.HR.exception.NotFoundException;
import com.example.HR.repository.client.ClientRepository;
import com.example.HR.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ClientConverter converter;

    @Override
    public ClientResponseDTO save(ClientRequestDTO dto) {
        List<String> memberNames = repository
                .findByCompanyName(dto.getCompanyName())
                .stream()
                .map(Client::getClientName)
                .collect(Collectors.toList());
        Client client = converter.toEntity(dto);
        client.setCompanyMembers(memberNames);
        client.setCreatedTime(LocalDateTime.now());

        Client savedClient = repository.save(client);
        savedClient.setClientId(String.format("#TC-%03d", savedClient.getId()));
        savedClient.setStatus(ClientStatus.ACTIVE);

        return converter.toResponseDTO(savedClient);

    }

    @Override
    public ClientResponseDTO update(Long id, ClientRequestDTO dto) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client no found by ID: " + id));
        converter.update(client,dto);

        Client savedClient = repository.save(client);

        return converter.toResponseDTO(savedClient);

    }

    @Override
    public ClientResponseDTO getById(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client no found by ID: " + id));

        return converter.toResponseDTO(client);
    }

    @Override
    public List<ClientResponseDTO> getAll() {

        List<Client> list = repository.findAll();
        return list.stream().map(client -> {
            List<String> companyMembers = list.stream()
                    .filter(c -> c.getCompanyName().equals(client.getCompanyName())
                            && !c.getId().equals(client.getId()))
                    .map(Client::getClientName)
                    .collect(Collectors.toList());

            ClientResponseDTO dto = converter.toResponseDTO(client);
            dto.setCompanyMembers(companyMembers);

            return dto;

        }).collect(Collectors.toList());

    }

    @Override
    public List<ClientResponseDTO> getByStatus(ClientStatus status) {
        List<Client> client = repository.findByStatus(status);

        return converter.toResponseDtoList(client);

    }

    @Override
    public void deleteById(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client no found by ID: " + id));

        repository.deleteById(id);
    }
}
