package com.example.HR.entity.employee;

import com.example.HR.entity.payroll.EmployeeSalary;
import com.example.HR.entity.task.Task;
import com.example.HR.entity.task.TaskAssignment;
import com.example.HR.entity.user.User;
import com.example.HR.enums.Departament;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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

    @Column(unique = true,name = "employee_id")
    private String employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "full_name_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private User fullname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "password_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private User password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private User email;

    @Enumerated(EnumType.STRING)
    @Column(name = "departament",nullable = false)
    private Departament departament;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<EmployeeAttachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskAssignment> taskAssignments = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EmployeeSalary> salaries;


    public Set<Task> getAssignedTasks() {
        return taskAssignments.stream()
                .map(TaskAssignment::getTask)
                .collect(Collectors.toSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // yalnız id istifadə et
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee other = (Employee) o;
        return Objects.equals(id, other.id);
    }
}
