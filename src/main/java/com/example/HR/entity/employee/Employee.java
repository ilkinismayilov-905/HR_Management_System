package com.example.HR.entity.employee;

import com.example.HR.entity.User;
import com.example.HR.enums.Departament;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
@Entity(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Phone number is invalid")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobTitle jobTitle;

    @Column(nullable = false)
    private LocalDate joinDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private String company;

    @Column(columnDefinition = "TEXT")
    private String about;

    @Column(nullable = false)
    private String employeeId;

    @OneToOne(optional = false)
    @JoinColumn(name = "full_name_id", nullable = false,unique = true)
    private User fullname;

    @OneToOne(optional = false)
    @JoinColumn(name = "password_id", nullable = false,unique = true)
    private User password;

    @OneToOne(optional = false)
    @JoinColumn(name = "email_id", nullable = false,unique = true)
    private User email;

    @Enumerated(EnumType.STRING)
    @Column(name = "departament",nullable = false)
    private Departament departament;
}
