package com.example.HR.converter;

import com.example.HR.dto.ticket.TicketRequestDTO;
import com.example.HR.dto.ticket.TicketResponseDTO;
import com.example.HR.entity.Ticket;
import com.example.HR.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketConverter {

    public TicketResponseDTO toResponseDTO(Ticket ticket){
        if(ticket == null) return  null;

        return TicketResponseDTO.builder()
                .id(ticket.getId())
                .email(ticket.getEmail())
                .status(ticket.getStatus())
                .description(ticket.getDescription())
                .ticketID(ticket.getTicketID())
                .assignedTo(ticket.getAssignedToFullName())
                .subject(ticket.getSubject())
//                .assignedToFullName(ticket.getAssignedToFullName())
                .build();
    }

    public List<TicketResponseDTO> toResponseDTOList(List<Ticket> list){
        return list.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Ticket toEntity(TicketRequestDTO dto, User user){
        return Ticket.builder()
                .id(dto.getId())
                .assignedTo(user)
                .subject(dto.getSubject())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .email(user.getEmail())
                .assignedToFullName(user.getFullname())
                .build();
    }

    public TicketRequestDTO toRequestDTO(Ticket ticket){
        return TicketRequestDTO.builder()
                .subject(ticket.getSubject())
                .description(ticket.getDescription())
                .assignedToFullName(ticket.getAssignedTo().getFullname())
                .status(ticket.getStatus())
                .build();
    }

    public void update(TicketRequestDTO dto,Ticket entity,User user){
        if(dto == null || entity == null){
            return;
        }

        if (dto.getSubject() != null) entity.setSubject(dto.getSubject());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());

        if (user != null) { // assignedTo dəyişirsə
            entity.setAssignedTo(user);
            entity.setEmail(user.getEmail());
        }
    }
}
