package com.example.HR.dto;

import com.example.HR.entity.User;
import com.example.HR.enums.Departament;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.transform.Source;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO{

    @Schema(description = "Full name of the employee", example = "John Doe")
    @NotBlank(message = "Full name is required")
    private String fullname;

    @Schema(description = "Unique employee ID", example = "EMP123456")
    @NotBlank(message = "Employee ID is required")
    private String employeeId;

    @Schema(description = "Joining date of the employee", example = "2023-01-15")
    @NotNull(message = "Join date is required")
    @PastOrPresent(message = "Join date cannot be in the future")
    private LocalDate joinDate;

    @Schema(description = "Username linked with the user account")
    @NotNull(message = "Username is required")
    private User userName;

    @Schema(description = "Email account object")
    @NotNull(message = "Email is required")
    private User email;

    @Schema(description = "Password object")
    @NotNull(message = "Password is required")
    private User password;

    @Schema(description = "Confirm password object")
    @NotNull(message = "Confirm password is required")
    private User confirmPassword;

    @Schema(description = "Phone number of the employee", example = "+994501234567")
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number")
    private String phoneNumber;

    @Schema(description = "Company name", example = "TechCorp LLC")
    @NotBlank(message = "Company is required")
    private String company;

    @Schema(description = "Department the employee belongs to")
    @NotNull(message = "Department is required")
    private Departament departament;

    @Schema(description = "Job title of the employee")
    @NotNull(message = "Job title is required")
    private JobTitle jobTitle;

    @Schema(description = "Short bio or description")
    private String about;

    @Schema(description = "Employment type", example = "FULL_TIME")
    @NotNull(message = "EmploymentType is required")
    private EmploymentType employmentType;

    @Schema(description = "Employee status", example = "ACTIVE")
    private Status status;

}
