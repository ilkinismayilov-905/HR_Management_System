package com.example.HR.entity;

import com.example.HR.enums.EmailStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "emails",
        indexes = {
                @Index(name = "idx_email_recipient", columnList = "recipient"),
                @Index(name = "idx_email_sender", columnList = "sender")
        })
@Data
public class EmailChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String sender;       // Göndərənin email ünvanı

    @Email
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String recipient;    // Alanın email ünvanı

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String subject;      // Mövzu

    @Lob
    @Column(nullable = false)
    private String body;         // Mesaj mətni

    @Column(nullable = false)
    private boolean read = false; // Oxunub?

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime sentAt; // Göndərilmə tarixi (avtomatik qoyulur)

    @UpdateTimestamp
    private LocalDateTime updatedAt; // Son yenilənmə tarixi (avtomatik)

    // Email statusunu idarə etmək üçün enum
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EmailStatus status = EmailStatus.SENT;

    // Optimistic locking üçün versiya sahəsi
    @Version
    private Long version;
}