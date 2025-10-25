package com.example.HR.dto.user;

import com.example.HR.enums.UserRoles;
import com.example.HR.validation.Create;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @Schema(description = "Unique identifier", example = "1")
    private Long id;

    @Schema(description = "Fullname for login", example = "johndoe", required = true)
    @NotBlank(groups = Create.class,message = "fullname is required")
    private String fullname;

    @Schema(description = "Password for login", required = true)
    @NotBlank(groups = Create.class,message = "Password is required")
    private String password;

    @Schema(description = "User roles assigned to the user", example = "ADMIN", allowableValues = {"USER", "ADMIN", "MODERATOR", "GUEST"}, required = true)
    @NotNull(groups = Create.class,message = "Role is required")
    private UserRoles roles;

    @Schema(description = "Email address", example = "johndoe@example.com", required = true)
    @Email(message = "Email should be valid")
    @NotBlank(groups = Create.class,message = "Email is required")
    private String email;

    @Schema(description = "Password confirmation", required = true)
    @NotBlank(groups = Create.class,message = "Confirm password is required")
    private String confirmPassword;

    public UserRequestDTO(String fullname,UserRoles roles, String email) {
        this.fullname = fullname;
        this.roles = roles;
        this.email = email;
    }
}
