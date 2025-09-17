package com.example.HR.entity.client;

import com.example.HR.enums.ClientStatus;
import com.example.HR.validation.Create;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clients")
@AllArgsConstructor
@NoArgsConstructor
@Data
@RequiredArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String clientId;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false,unique = true)
    private String companyName;

    @Email(message = "Email should be valid")
    @NotBlank(groups = Create.class,message = "Email is required")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number")
    private String phoneNumber;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private List<String> companyMembers;

    @Enumerated(EnumType.STRING)
    private ClientStatus status;
}
