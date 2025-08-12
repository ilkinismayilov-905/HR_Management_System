//package com.example.HR.entity.employee;
//
//import com.example.HR.enums.Departament;
//import com.example.HR.enums.EmploymentType;
//import com.example.HR.enums.JobTitle;
//import com.example.HR.enums.Status;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//
//@Entity(name = "employee_information")
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//@Builder
//public class EmployeeInformation {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne
//    private Employee employee;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "job_title",nullable = false)
//    private JobTitle jobTitle;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "employee_type",nullable = false)
//    private EmploymentType employmentType;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status",nullable = false)
//    private Status status;
//
//    public LocalDate getJoinDate(){
//        return employee != null ? employee.getJoinDate() : null;
//    }
//
//    public String getEmployeeId(){
//        return employee != null ? employee.getEmployeeId() : null;
//    }
//
//    public Departament getDepartament(){
//        return employee != null ? employee.getDepartament() : null;
//    }
//
//
//}
