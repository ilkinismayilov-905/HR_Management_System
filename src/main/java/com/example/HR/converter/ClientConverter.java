package com.example.HR.converter;

import com.example.HR.dto.client.ClientRequestDTO;
import com.example.HR.dto.client.ClientResponseDTO;
import com.example.HR.entity.client.Client;
import org.springframework.stereotype.Component;

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

    public void update(Client entity, ClientRequestDTO dto){
        if(entity == null || dto == null) return;

        if(dto.getId() != null) entity.setId(dto.getId());
        if(dto.getFullname() != null) entity.setClientName(dto.getFullname());
        if(dto.getCompanyName() != null) entity.setCompanyName(dto.getCompanyName());
    }

}
