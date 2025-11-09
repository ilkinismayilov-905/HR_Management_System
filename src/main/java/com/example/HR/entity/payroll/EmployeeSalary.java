package com.example.HR.entity.payroll;

import com.example.HR.entity.employee.Employee;
import com.example.HR.enums.SalaryStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employee_salaries")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;

    @Column(name = "net_salary")
    private BigDecimal netSalary;

    // Earnings
    @Column(name = "basic_salary")
    private BigDecimal basicSalary;

    @Column(name = "conveyance")
    private BigDecimal conveyance;

    @Column(name = "hra")
    private BigDecimal hra; // House Rent Allowance

    @Column(name = "allowance")
    private BigDecimal allowance;

    @Column(name = "medical_allowance")
    private BigDecimal medicalAllowance;

    @Column(name = "overtime_amount")
    private BigDecimal overtimeAmount;

    @Column(name = "bonus")
    private BigDecimal bonus;

    // Deductions
    @Column(name = "tds")
    private BigDecimal tds; // Tax Deducted at Source

    @Column(name = "leave_deduction")
    private BigDecimal leaveDeduction;

    @Column(name = "prof_tax")
    private BigDecimal profTax;

    @Column(name = "labour_welfare")
    private BigDecimal labourWelfare;

    @Column(name = "provident_fund")
    private BigDecimal providentFund;

    @Column(name = "esi")
    private BigDecimal esi; // Employee State Insurance

    @Column(name = "gross_salary")
    private BigDecimal grossSalary;

    @Column(name = "total_deductions")
    private BigDecimal totalDeductions;

    @Column(name = "final_net_salary")
    private BigDecimal finalNetSalary;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SalaryStatus status;
}
