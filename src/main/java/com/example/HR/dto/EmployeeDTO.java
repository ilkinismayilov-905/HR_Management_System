package com.example.HR.dto;

import com.example.HR.enums.Departament;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.transform.Source;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeDTO{
    @NotBlank(message = "Full name is required")
    private String fullname;

    @NotBlank(message = "Employee ID is required")
    private String employeeId;

    @NotNull(message = "Join date is required")
    @PastOrPresent(message = "Join date cannot be in the future")
    private LocalDate joinDate;

    @NotBlank(message = "Username is required")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "Company is required")
    private String company;

    @NotNull(message = "Department is required")
    private Departament departament;

    @NotNull(message = "Job title is required")
    private JobTitle jobTitle;

    private String about;
}
