package com.example.HR.entity.employee;

import com.example.HR.entity.User;
import com.example.HR.enums.Departament;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false,unique = true)
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Emergency contact number is invalid")
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
    private String EmployeeId;

    @OneToOne
    @Column(nullable = false)
    private User userName;

    @OneToOne
    @Column(nullable = false)
    private User password;

    @OneToOne
    @Column(nullable = false)
    private User confirmPassword;

    @OneToOne
    @Column(nullable = false)
    private User email;

    @Enumerated(EnumType.STRING)
    @Column(name = "departament",nullable = false)
    private Departament departament;

    public Employee(String fullname, String employeeId, LocalDate joinDate,
                    User userName, User email, User password, User confirmPassword,
                    String phoneNumber, String company, Departament departament,
                    JobTitle jobTitle, String about) {
        this.fullname = fullname;
        EmployeeId = employeeId;
        this.joinDate = joinDate;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.departament = departament;
        this.jobTitle = jobTitle;
        this.about = about;
    }
}
