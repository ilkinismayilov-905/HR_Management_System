package com.example.HR.entity;

import com.example.HR.enums.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "fullname"),
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "password")
})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements UserDetails {
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

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    private UserRoles roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + roles.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
