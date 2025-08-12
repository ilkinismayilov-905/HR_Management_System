package com.example.HR.dto;

import com.example.HR.enums.Departament;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.lang.reflect.DeclareParents;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeInformationDTO {

    private String employeeId;

    private JobTitle jobTitle;

    private EmploymentType employmentType;

    private Departament departament;

    private LocalDate joinDate;

    private Status status;
}
