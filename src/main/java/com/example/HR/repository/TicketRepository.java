package com.example.HR.repository;

import com.example.HR.entity.Ticket;
import com.example.HR.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByCreatedAtBetween(LocalDate startDate, LocalDate endDate);
}
