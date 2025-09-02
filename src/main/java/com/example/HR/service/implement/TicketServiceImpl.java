package com.example.HR.service.implement;


import com.example.HR.converter.TicketConverter;
import com.example.HR.dto.ticket.TicketCommentDTO;
import com.example.HR.dto.ticket.TicketRequestDTO;
import com.example.HR.dto.ticket.TicketResponseDTO;
import com.example.HR.entity.ticket.Ticket;
import com.example.HR.entity.User;
import com.example.HR.entity.ticket.TicketAttachment;
import com.example.HR.entity.ticket.TicketComment;
import com.example.HR.enums.TicketStatus;
import com.example.HR.exception.NotFoundException;
import com.example.HR.exception.ResourceNotFoundException;
import com.example.HR.repository.ticket.TicketCommentRepository;
import com.example.HR.repository.ticket.TicketRepository;
import com.example.HR.repository.UserRepository;
import com.example.HR.service.TicketService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketConverter converter;
    private final TicketCommentRepository commentRepository;
    private final FileStorageService fileStorageService;

    @Override
    public TicketResponseDTO create(TicketRequestDTO dto) {

        log.info("Searching for user with fullname: '{}'", dto.getAssignedToFullName());

        if (dto.getAssignedToFullName() == null ||
                dto.getAssignedToFullName().trim().isEmpty()) {
            throw new RuntimeException("Assigned user full name cannot be null or empty");
        }

        String fullname = dto.getAssignedToFullName();

            User user = userRepository.findByFullname(fullname)
                .orElseThrow(() -> new RuntimeException("User not found with fullname: " + fullname));

        log.info("Found user: {}", user.getFullname());


        Ticket ticket = converter.toEntity(dto,user);
        ticket.setEmail(user.getEmail());
        ticket.setCreatedAt(LocalDate.now());
        Ticket savedTicket = ticketRepository.save(ticket);
        savedTicket.setTicketID(String.format("#TC-%03d", savedTicket.getId()));

        log.info("Ticket created successfully: {}",savedTicket.getTicketID());


        return converter.toResponseDTO(savedTicket);
    }

    @Override
    public List<TicketResponseDTO> getAll() {
        log.info("Fetch all tickets");
        List<Ticket> list = ticketRepository.findAll();

        return converter.toResponseDTOList(list);
    }

    @Override
    public TicketResponseDTO getById(Long id) {
        log.info("Get ticket by id: {}" , id);
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found by id: " + id));

        log.info("Ticket successfully fetched by id: {}", id);
        return converter.toResponseDTO(ticket);

    }

    @Override
    public TicketResponseDTO update(Long id, TicketRequestDTO dto) {
        log.info("Update ticket by ID: {}", id);
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found by id: " + id));

        User user = userRepository.findByFullname(ticket.getAssignedToFullName())
                        .orElseThrow(() -> new NotFoundException("User not found by fullname: " + dto.getAssignedToFullName()));
        converter.update(dto,ticket,user);
        log.info("Ticket updated successfully");

        Ticket savedTicket = ticketRepository.save(ticket);
        log.info("Updated ticket saved successfully");

        return converter.toResponseDTO(savedTicket);
    }

    @Override
    public List<TicketResponseDTO> getByStatus(TicketStatus status) {

        List<Ticket> ticket = ticketRepository.findByStatus(status);
        log.info("Tickets fetched by status");

        return converter.toResponseDTOList(ticket);

    }

    @Override
    public List<TicketResponseDTO> getTicketsFromLastDays(int days) {

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days);

        List<Ticket> ticket = ticketRepository.findByCreatedAtBetween(startDate,endDate);

        return converter.toResponseDTOList(ticket);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete ticket by id: {}" , id);
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found by id: " + id));

        ticketRepository.deleteById(id);
        log.info("Ticket successfully deleted by id: {}", id);

    }

    @Override
    public TicketAttachment uploadAttachment(String ticketId, MultipartFile file) {
        Ticket ticket = ticketRepository.findByTicketID(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + ticketId));

        TicketAttachment attachment = fileStorageService.storeFile(file,ticket);
        log.info("Uploaded attachment {} for ticket {}", file.getOriginalFilename(), ticketId);

        return attachment;
    }

    @Override
    public TicketCommentDTO addComment(String ticketId, String content, String authorName, String authorEmail) {
        Ticket ticket = ticketRepository.findByTicketID(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + ticketId));

        TicketComment comment = TicketComment.builder()
                .content(content)
                .authorName(authorName)
                .authorEmail(authorEmail)
                .ticket(ticket)
                .build();

        comment = commentRepository.save(comment);
        log.info("Added comment to ticket: {}", ticketId);

        return converter.mapCommentToDTO(comment);
    }
}
