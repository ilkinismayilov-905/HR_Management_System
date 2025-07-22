package com.example.HR.dto;

import com.example.HR.enums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private UserRoles roles;
    private String email;
    private String confirmPassword;

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
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public UserRoles getRoles() {
//        return roles;
//    }
//
//    public void setRoles(UserRoles roles) {
//        this.roles = roles;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getConfirmPassword() {
//        return confirmPassword;
//    }
//
//    public void setConfirmPassword(String confirmPassword) {
//        this.confirmPassword = confirmPassword;
//    }

}
