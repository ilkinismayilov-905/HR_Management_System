package com.example.HR.dto.payroll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeSalaryDTO {
    private Long employeeId;
    private String employeeName;
    private BigDecimal netSalary;

    // Earnings
    private BigDecimal basicSalary;
    private BigDecimal conveyance;
    private BigDecimal hra;
    private BigDecimal allowance;
    private BigDecimal medicalAllowance;

    // Deductions
    private BigDecimal tds;
    private BigDecimal leaveDeduction;
    private BigDecimal profTax;
    private BigDecimal labourWelfare;

    private BigDecimal grossSalary;
    private BigDecimal totalDeductions;
    private BigDecimal finalNetSalary;
}
