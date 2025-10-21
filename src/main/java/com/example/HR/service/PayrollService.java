package com.example.HR.service;


import com.example.HR.dto.payroll.EmployeeSalaryRequestDTO;
import com.example.HR.dto.payroll.EmployeeSalaryResponseDTO;
import com.example.HR.dto.payroll.PayrollItemDTO;
import com.example.HR.entity.payroll.PayrollItem;
import com.example.HR.enums.payroll.PayrollCategory;

import java.math.BigDecimal;
import java.util.List;

public interface PayrollService {
    public BigDecimal calculateHRA(BigDecimal basicSalary);
    public BigDecimal calculateTDS(BigDecimal annualIncome);
    public BigDecimal calculateProfTax(BigDecimal monthlySalary);
    public List<PayrollItemDTO> getPayrollItems();
    public List<PayrollItemDTO> getPayrollItemsByCategory(PayrollCategory category);
    public void deletePayrollItem(Long itemId);
    public PayrollItem updatePayrollItem(Long itemId, PayrollItem updatedItem);
    public PayrollItem createPayrollItem(PayrollItem item);
    public void deleteEmployeeSalary(Long salaryId);
    public EmployeeSalaryResponseDTO createEmployeeSalary(EmployeeSalaryRequestDTO requestDTO);
    public EmployeeSalaryResponseDTO getEmployeeSalaryByEmployeeId(Long employeeId);
    public List<EmployeeSalaryResponseDTO> getEmployeeSalaryHistory(Long employeeId);
    public EmployeeSalaryResponseDTO updateEmployeeSalary(Long salaryId, EmployeeSalaryRequestDTO requestDTO);
}
