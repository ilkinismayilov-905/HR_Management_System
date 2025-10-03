package com.example.HR.dto.payroll;

import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeSalaryResponseDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;

    // Earnings
    private BigDecimal basicSalary;
    private BigDecimal conveyance;
    private BigDecimal hra;
    private BigDecimal allowance;
    private BigDecimal medicalAllowance;
    private BigDecimal overtimeAmount;
    private BigDecimal bonus;

    // Deductions
    private BigDecimal tds;
    private BigDecimal leaveDeduction;
    private BigDecimal profTax;
    private BigDecimal labourWelfare;
    private BigDecimal providentFund;

    // Calculated fields
    private BigDecimal grossSalary;
    private BigDecimal totalDeductions;
    private BigDecimal finalNetSalary;

    private LocalDate effectiveDate;
    private String status;
}
