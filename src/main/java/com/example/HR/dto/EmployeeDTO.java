package com.example.HR.dto;

import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobPosition;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import javax.xml.transform.Source;
import java.time.LocalDate;

@Data
public class EmployeeDTO implements Source {
    @NotBlank(message = "Full name cannot be blank")
    private String fullname;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    @NotNull(message = "Job position must be selected")
    private JobPosition jobPosition;

    @NotNull(message = "Join date cannot be null")
    private LocalDate joinDate;

    @NotNull(message = "Employment type must be selected")
    private EmploymentType employmentType;

    @Override
    public void setSystemId(String systemId) {

    }

    @Override
    public String getSystemId() {
        return "";
    }
}
