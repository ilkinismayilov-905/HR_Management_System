package com.example.HR.service;

import com.example.HR.dto.ticket.TicketRequestDTO;
import com.example.HR.dto.ticket.TicketResponseDTO;
import com.example.HR.entity.Ticket;

import java.util.List;

public interface TicketService {
    TicketResponseDTO create(TicketRequestDTO ticket);
    List<TicketResponseDTO> getAll();
    TicketResponseDTO getById(Long id);
    TicketResponseDTO update(Long id,TicketRequestDTO dto);
    void deleteById(Long id);
}
