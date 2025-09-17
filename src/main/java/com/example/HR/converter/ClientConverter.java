package com.example.HR.converter;

import com.example.HR.dto.client.ClientRequestDTO;
import com.example.HR.dto.client.ClientResponseDTO;
import com.example.HR.entity.client.Client;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientConverter {

    public ClientResponseDTO toResponseDTO(Client entity){
        if(entity == null) return null;

        return ClientResponseDTO.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .email(entity.getEmail())
                .clientId(entity.getClientId())
                .clientName(entity.getClientName())
                .phoneNumber(entity.getPhoneNumber())
                .companyName(entity.getCompanyName())
                .companyMembers(entity.getCompanyMembers())
                .build();
    }

    public List<ClientResponseDTO> toResponseDtoList(List<Client> list){
        return list.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ClientRequestDTO toRequestDTO(Client entity){
        if(entity == null) return null;

        return ClientRequestDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .fullname(entity.getClientName())
                .phoneNumber(entity.getPhoneNumber())
                .companyName(entity.getCompanyName())
                .build();
    }

    public Client toEntity(ClientRequestDTO dto){
        if(dto == null ) return null;

        return Client.builder()
                .clientName(dto.getFullname())
                .companyName(dto.getCompanyName())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .build();
    }

    public List<Client> toEntityList(List<ClientRequestDTO> list){
        return list.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void update(Client entity, ClientRequestDTO dto){
        if(entity == null || dto == null) return;

        if(dto.getId() != null) entity.setId(dto.getId());
        if(dto.getFullname() != null) entity.setClientName(dto.getFullname());
        if(dto.getCompanyName() != null) entity.setCompanyName(dto.getCompanyName());
        if(dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if(dto.getPhoneNumber() != null) entity.setPhoneNumber(dto.getPhoneNumber());
    }

    public ClientResponseDTO mapCompanyMember (Client client){
        if(client == null) return null;

        return ClientResponseDTO.builder()
                .id(client.getId())
                .clientName(client.getClientName())
                .build();
    }

    public List<ClientResponseDTO> mapCompanyMemberList(List<Client> list){
        return list.stream()
                .map(this::mapCompanyMember)
                .collect(Collectors.toList());
    }

}
