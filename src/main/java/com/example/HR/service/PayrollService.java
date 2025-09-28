package com.example.HR.service;


import com.example.HR.dto.payroll.EmployeeSalaryDTO;
import com.example.HR.dto.payroll.PayrollItemDTO;
import com.example.HR.entity.payroll.EmployeeSalary;

import java.math.BigDecimal;
import java.util.List;

public interface PayrollService {
    public BigDecimal calculateHRA(BigDecimal basicSalary);
    public BigDecimal calculateTDS(BigDecimal annualIncome);
    public BigDecimal calculateProfTax(BigDecimal monthlySalary);
    public EmployeeSalary createEmployeeSalary(EmployeeSalaryDTO salaryDTO);
    public List<PayrollItemDTO> getPayrollItems();
}
