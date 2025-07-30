package com.example.HR.dto;

import com.example.HR.enums.UserRoles;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

//@Setter
//@Getter
//@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    //    public UserDTO() {
    //    }
    //
    //    public UserDTO(Long id,
    //                   String username,
    //                   String password,
    //                   UserRoles roles,
    //                   String email,
    //                   String confirmPassword) {
    //        this.id = id;
    //        this.username = username;
    //        this.password = password;
    //        this.roles = roles;
    //        this.email = email;
    //        this.confirmPassword = confirmPassword;
    //    }
    //
    @Schema(description = "Unique identifier", example = "1")
    private Long id;

    @Schema(description = "Fullname for login", example = "johndoe", required = true)
    @NotBlank(message = "fullname is required")
    private String fullname;

    @Schema(description = "Password for login", required = true)
    @NotBlank(message = "Password is required")
    private String password;

//    @Schema(description = "User roles assigned to the user", example = "ADMIN", allowableValues = {"USER", "ADMIN", "MODERATOR", "GUEST"}, required = true)
//    @NotNull(message = "Role is required")
//    private UserRoles roles;

    @Schema(description = "Email address", example = "johndoe@example.com", required = true)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Schema(description = "Password confirmation", required = true)
    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

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

//    public UserRoles getRoles() {
//        return roles;
//    }
//
//    public void setRoles(UserRoles roles) {
//        this.roles = roles;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
