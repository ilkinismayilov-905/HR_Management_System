package com.example.HR.dto.user;

import com.example.HR.enums.UserRoles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDTO {
   private String email;
   private String fullname;
   private Boolean active;

   @Enumerated(value = EnumType.STRING)
    private UserRoles roles;


}
