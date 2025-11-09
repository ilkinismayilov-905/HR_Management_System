package com.example.HR.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @Email(message = "Email format yanlışdır")
    @NotBlank(message = "Email boş ola bilməz")
    private String email;

    @NotBlank(message = "Password boş ola bilməz")
    @Size(min = 6, message = "Password minimum 6 karakter olmalıdır")
    private String password;
}
