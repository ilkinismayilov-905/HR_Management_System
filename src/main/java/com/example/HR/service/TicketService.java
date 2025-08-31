package com.example.HR.service;

import com.example.HR.dto.ticket.TicketRequestDTO;
import com.example.HR.dto.ticket.TicketResponseDTO;
import com.example.HR.entity.Ticket;
import com.example.HR.enums.TicketStatus;

import java.util.List;

public interface TicketService {
    TicketResponseDTO create(TicketRequestDTO ticket);
    List<TicketResponseDTO> getAll();
    TicketResponseDTO getById(Long id);
    TicketResponseDTO update(Long id,TicketRequestDTO dto);
    List<TicketResponseDTO> getByStatus(TicketStatus status);
    List<TicketResponseDTO> getTicketsFromLastDays(int days);
    void deleteById(Long id);
}
