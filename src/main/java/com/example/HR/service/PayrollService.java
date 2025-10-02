package com.example.HR.service;


import com.example.HR.dto.payroll.EmployeeSalaryDTO;
import com.example.HR.dto.payroll.PayrollItemDTO;
import com.example.HR.entity.payroll.EmployeeSalary;
import com.example.HR.entity.payroll.PayrollItem;
import com.example.HR.enums.PayrollCategory;

import java.math.BigDecimal;
import java.util.List;

public interface PayrollService {
    public BigDecimal calculateHRA(BigDecimal basicSalary);
    public BigDecimal calculateTDS(BigDecimal annualIncome);
    public BigDecimal calculateProfTax(BigDecimal monthlySalary);
    public EmployeeSalary createEmployeeSalary(EmployeeSalaryDTO salaryDTO);
    public List<PayrollItemDTO> getPayrollItems();
    public EmployeeSalary getEmployeeSalaryByEmployeeId(Long employeeId);
    public List<PayrollItemDTO> getPayrollItemsByCategory(PayrollCategory category);
    public void deletePayrollItem(Long itemId);
    public PayrollItem updatePayrollItem(Long itemId, PayrollItem updatedItem);
    public PayrollItem createPayrollItem(PayrollItem item);
    public void deleteEmployeeSalary(Long salaryId);
    public List<EmployeeSalary> getEmployeeSalaryHistory(Long employeeId) ;
    public EmployeeSalary updateEmployeeSalary(Long salaryId, EmployeeSalaryDTO salaryDTO);
}
