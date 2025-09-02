package com.example.HR.service;

import com.example.HR.dto.ticket.TicketCommentDTO;
import com.example.HR.dto.ticket.TicketRequestDTO;
import com.example.HR.dto.ticket.TicketResponseDTO;
import com.example.HR.entity.ticket.TicketAttachment;
import com.example.HR.enums.TicketStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TicketService {
    TicketResponseDTO create(TicketRequestDTO ticket);
    List<TicketResponseDTO> getAll();
    TicketResponseDTO getById(Long id);
    TicketResponseDTO update(Long id,TicketRequestDTO dto);
    List<TicketResponseDTO> getByStatus(TicketStatus status);
    List<TicketResponseDTO> getTicketsFromLastDays(int days);
    void deleteById(Long id);
    TicketAttachment uploadAttachment(String ticketId, MultipartFile file);
    TicketCommentDTO addComment(String ticketId, String content, String authorName, String authorEmail);
}
