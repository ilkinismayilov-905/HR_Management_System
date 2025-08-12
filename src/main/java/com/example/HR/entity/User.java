package com.example.HR.entity;

import com.example.HR.enums.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "users")
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "fullname"),
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "password")
})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false,unique = true)
    private String fullname;

    @NotBlank
    @Column(unique = true,nullable = false)
    private String password;

    @Column(nullable = false)
    @Email
    private String email;

    private String otp;

    private LocalDateTime createdTime;

    @Column(nullable = true)
    private boolean active;

    @Enumerated(EnumType.STRING)
    private UserRoles roles;
}
