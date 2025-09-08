package com.example.HR.dto.employee;

import com.example.HR.dto.ticket.TicketAttachmentDTO;
import com.example.HR.enums.Departament;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import com.example.HR.validation.Create;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeResponseDTO {

    private Long id;

    @Schema(description = "Full name of the employee", example = "John Doe", required = true)
    @NotBlank(groups = Create.class,message = "Full name is required")
    private String fullname;

    @Schema(description = "Unique employee ID", example = "EMP123456", required = true)
    @NotBlank(groups = Create.class,message = "Employee ID is required")
    private String employeeId;

    @Schema(description = "Joining date of the employee", example = "2023-01-15", required = true, type = "string", format = "date")
    @NotNull(groups = Create.class,message = "Join date is required")
    @PastOrPresent(message = "Join date cannot be in the future")
    private LocalDate joinDate;

    @Schema(description = "Email account of the employee", required = true, example = "johndoe@example.com")
    @NotNull(groups = Create.class,message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "Phone number of the employee", example = "+994501234567", required = true)
    @NotBlank(groups = Create.class,message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number")
    private String phoneNumber;

    @Schema(description = "Company name", example = "TechCorp LLC", required = true)
    @NotBlank(groups = Create.class,message = "Company is required")
    private String company;

    @Schema(description = "Department the employee belongs to", required = true, example = "IT", allowableValues = {"IT", "HR", "SALES", "FINANCE", "MARKETING", "OPERATIONS", "DESIGN", "PROCUREMENT", "ENGINEERING", "CUSTOMER_SERVICE"})
    @NotNull(groups = Create.class,message = "Department is required")
    private Departament departament;

    @Schema(description = "Job title of the employee", required = true, example = "DEVELOPER", allowableValues = {"DEVELOPER", "CEO", "UX_DESIGNER", "PROJECT_MANAGER", "PRODUCTS_DESIGNER", "ILLUSTRATOR", "UI_DESIGNER"})
    @NotNull(groups = Create.class,message = "Job title is required")
    private JobTitle jobTitle;

    @Schema(description = "Short bio or description", example = "Hardworking and reliable employee")
    private String about;

    @Schema(description = "Employment type", example = "FULL_TIME", required = true, allowableValues = {"FULL_TIME", "PART_TIME", "INTERNSHIP" ,"REMOTE", "HYBRID", "CONTRACT"})
    @NotNull(groups = Create.class,message = "Employment type is required")
    private EmploymentType employmentType;

    @Schema(description = "Employee status", example = "ACTIVE", allowableValues = {"ACTIVE", "INACTIVE", "SUSPENDED"})
    private Status status;

    private List<EmployeeAttachmentDTO> attachments;
    private List<EmployeeTaskResponseDTO> tasks;

}
