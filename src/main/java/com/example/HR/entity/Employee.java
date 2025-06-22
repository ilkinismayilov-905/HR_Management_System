package com.example.HR.entity;

import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobPosition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;
    private String email;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private JobPosition jobPosition;

    private LocalDate joinDate;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;
}
