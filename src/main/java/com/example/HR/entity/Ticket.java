package com.example.HR.entity;

import com.example.HR.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "ticket")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_ID",unique = true)
    private String ticketID;

    @Column(nullable = false,name = "subject")
    private String subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_id", nullable = false)
    private User assignedTo;

    @Column(name = "assigned_to_full_name")
    private String assignedToFullName;

    @Column(nullable = false,name = "description",length = 100)
    private String description;

    @Column(nullable = false,name = "status")
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(nullable = false,name = "email")
    private String email;

    @CreationTimestamp
    @Column(nullable = false,name = "created_date")
    private LocalDate createdAt;

}
