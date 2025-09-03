package com.example.HR.repository.ticket;

import com.example.HR.entity.ticket.Ticket;
import com.example.HR.enums.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByCreatedAtBetween(LocalDate startDate, LocalDate endDate);
    Optional<Ticket> findByTicketID(String ticketId);

    @Query("SELECT t FROM Ticket t WHERE " +
            "(:subject IS NULL OR LOWER(t.subject) LIKE LOWER(CONCAT('%', :subject, '%'))) AND " +
            "(:email IS NULL OR LOWER(t.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:status IS NULL OR t.status = :status)")
    List<Ticket> searchTickets(@Param("subject") String subject,
                               @Param("email") String email,
                               @Param("status") TicketStatus status);
}
