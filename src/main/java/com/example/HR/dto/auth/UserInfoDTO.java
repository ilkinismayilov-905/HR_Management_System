package com.example.HR.dto.auth;

import com.example.HR.enums.UserRoles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserInfoDTO {
    private Long id;
    private String email;
    private String fullname;
    private Boolean active;
    private String role;
}
