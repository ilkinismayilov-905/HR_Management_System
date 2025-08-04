package com.example.HR.entity;

import com.example.HR.enums.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

//@Setter
//@Getter
@Entity(name = "users")
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "fullname"),
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "password")
})
@AllArgsConstructor
@NoArgsConstructor
//@Data
public class User {

    //    public User(Long id, String username, String password, String confirmPassword, String email, UserRoles roles) {
    //        this.id = id;
    //        this.username = username;
    //        this.password = password;
    //        this.confirmPassword = confirmPassword;
    //        this.email = email;
    //        this.roles = roles;
    //    }
    //
    //    public User() {
    //    }
    //
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

    @Column(nullable = false)
    private boolean active;

    @Enumerated(EnumType.STRING)
    private UserRoles roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
        public UserRoles getRoles() {
        return roles;
    }

    public void setRoles(UserRoles roles) {
        this.roles = roles;
    }
}
