package com.example.HR.service.implement;

import com.example.HR.converter.ClientConverter;
import com.example.HR.dto.client.ClientInformationDTO;
import com.example.HR.dto.client.ClientProjectsDTO;
import com.example.HR.dto.client.ClientRequestDTO;
import com.example.HR.dto.client.ClientResponseDTO;
import com.example.HR.entity.client.Client;
import com.example.HR.entity.client.ClientAttachment;
import com.example.HR.entity.project.Project;
import com.example.HR.enums.ClientStatus;
import com.example.HR.exception.NotFoundException;
import com.example.HR.exception.ResourceNotFoundException;
import com.example.HR.repository.client.ClientRepository;
import com.example.HR.repository.project.ProjectRepository;
import com.example.HR.service.ClientService;
import com.example.HR.service.implement.fileStorage.ClientImagesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final ClientImagesService storageService;
    private final ProjectRepository projectRepository;

    @Override
    public ClientResponseDTO save(ClientRequestDTO dto) {
        log.info("Save new client.");
        List<String> memberNames = repository
                .findByCompanyName(dto.getCompanyName())
                .stream()
                .map(Client::getClientName)
                .collect(Collectors.toList());
        Client client = converter.toEntity(dto);
        log.info("Client converted to Entity");

        client.setCompanyMembers(memberNames);
        client.setCreatedTime(LocalDateTime.now());

        Client savedClient = repository.save(client);
        savedClient.setClientId(String.format("Cli-%03d", savedClient.getId()));
        savedClient.setStatus(ClientStatus.ACTIVE);
        log.info("Client saved.");

        return converter.toResponseDTO(savedClient);

    }

    @Override
    public ClientResponseDTO update(Long id, ClientRequestDTO dto) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client no found by ID: " + id));
        log.info("Client found by ID");

        converter.update(client,dto);
        log.info("Client converted");

        Client savedClient = repository.save(client);
        log.info("Client saved");

        return converter.toResponseDTO(savedClient);

    }

    @Override
    public ClientResponseDTO getById(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client no found by ID: " + id));
        log.info("Client found by ID");

        return converter.toResponseDTO(client);
    }

    @Override
    public List<ClientResponseDTO> getAll() {

        List<Client> list = repository.findAll();
        log.info("Clients found");
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
        log.info("Client found by status");

        return converter.toResponseDtoList(client);

    }

    @Override
    public void deleteById(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client no found by ID: " + id));
        log.info("Client found by ID");

        repository.deleteById(id);
    }

    @Override
    public ClientAttachment uploadAttachment(String clientId, MultipartFile file) {
        Client client = repository.findByClientId(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + clientId));
        log.info("Client found by ID");

        ClientAttachment attachment = storageService.storeFile(file,client);
        log.info("Uploaded attachment {} for task {}", file.getOriginalFilename(), clientId);

        return attachment;
    }

    @Override
    public ClientInformationDTO getClientInfo(String clientId) {
        Client client = repository.findByClientId(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found by clientId: " + clientId));
        log.info("Client found by ID");

        ClientInformationDTO convertedClient = converter.mapToInfoDTO(client);
        log.info("Client converted to InformationDTO");

        return convertedClient;
    }

    @Override
    public List<ClientProjectsDTO> getCurrentProjects(String username) {

        List<Project> list = projectRepository.findByClient_ClientName(username);
        log.info("Client found by ClientName");

        return converter.projectsDTOList(list);
    }

}
