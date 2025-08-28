package com.example.HR.service.implement;


import com.example.HR.converter.TicketConverter;
import com.example.HR.dto.ticket.TicketRequestDTO;
import com.example.HR.dto.ticket.TicketResponseDTO;
import com.example.HR.entity.Ticket;
import com.example.HR.entity.User;
import com.example.HR.exception.NotFoundException;
import com.example.HR.repository.TicketRepository;
import com.example.HR.repository.UserRepository;
import com.example.HR.service.TicketService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketConverter converter;

    @Override
    public TicketResponseDTO create(TicketRequestDTO dto) {

        log.info("Searching for user with fullname: '{}'", dto.getAssignedToFullName());

        if (dto.getAssignedToFullName() == null ||
                dto.getAssignedToFullName().trim().isEmpty()) {
            throw new RuntimeException("Assigned user full name cannot be null or empty");
        }

        String trimmedName = dto.getAssignedToFullName().trim();
        log.info("Trimmed fullname: '{}'", trimmedName);

        User user = userRepository.findByFullname(trimmedName)
                .orElseThrow(() -> new RuntimeException("User not found with fullname: " + trimmedName));

        log.info("Found user: {}", user.getFullname());


        Ticket ticket = converter.toEntity(dto,user);
        ticket.setEmail(user.getEmail());
        Ticket savedTicket = ticketRepository.save(ticket);
        savedTicket.setTicketID(String.format("#TC-%03d", savedTicket.getId()));

        log.info("Ticked created successfully: {}",savedTicket.getTicketID());


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
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found by id: " + id));

        User user = userRepository.findByFullname(dto.getAssignedToFullName())
                        .orElseThrow(() -> new NotFoundException("User not found by fullname: " + dto.getAssignedToFullName()));
        converter.update(dto,ticket,user);

        Ticket savedTicket = ticketRepository.save(ticket);

        return converter.toResponseDTO(savedTicket);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete ticket by id: {}" , id);
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found by id: " + id));

        ticketRepository.deleteById(id);
        log.info("Ticket successfully deleted by id: {}", id);

    }
}
