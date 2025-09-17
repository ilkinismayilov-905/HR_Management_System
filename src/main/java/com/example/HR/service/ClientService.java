package com.example.HR.service;


import com.example.HR.dto.client.ClientRequestDTO;
import com.example.HR.dto.client.ClientResponseDTO;
import com.example.HR.dto.employee.EmployeeResponseDTO;
import com.example.HR.enums.ClientStatus;
import com.example.HR.enums.Status;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    public ClientResponseDTO save(ClientRequestDTO dto);
    public ClientResponseDTO update(Long id,ClientRequestDTO dto);
    public ClientResponseDTO getById(Long id);
    public List<ClientResponseDTO> getAll();
    public List<ClientResponseDTO> getByStatus(ClientStatus status);
    void deleteById(Long id);
}
