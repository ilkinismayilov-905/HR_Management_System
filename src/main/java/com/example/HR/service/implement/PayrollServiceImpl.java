package com.example.HR.service.implement;

import com.example.HR.converter.PayrollConverter;
import com.example.HR.dto.payroll.EmployeeSalaryDTO;
import com.example.HR.dto.payroll.PayrollItemDTO;
import com.example.HR.entity.employee.Employee;
import com.example.HR.entity.payroll.EmployeeSalary;
import com.example.HR.entity.payroll.PayrollItem;
import com.example.HR.enums.PayrollCategory;
import com.example.HR.enums.SalaryStatus;
import com.example.HR.repository.employee.EmployeeRepository;
import com.example.HR.repository.payroll.EmployeeSalaryRepository;
import com.example.HR.repository.payroll.PayrollItemRepository;
import com.example.HR.service.PayrollService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Transient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transient
@Slf4j
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PayrollItemRepository payrollItemRepository;

    @Autowired
    private EmployeeSalaryRepository employeeSalaryRepository;

    @Autowired
    private PayrollConverter converter;

    @Override
    public BigDecimal calculateHRA(BigDecimal basicSalary) {
        return basicSalary.multiply(BigDecimal.valueOf(0.15));
    }

    @Override
    public BigDecimal calculateTDS(BigDecimal annualIncome) {
        if (annualIncome.compareTo(BigDecimal.valueOf(250000)) <= 0) {
            return BigDecimal.ZERO;
        } else if (annualIncome.compareTo(BigDecimal.valueOf(500000)) <= 0) {
            return annualIncome.subtract(BigDecimal.valueOf(250000))
                    .multiply(BigDecimal.valueOf(0.05));
        } else {
            return BigDecimal.valueOf(12500)
                    .add(annualIncome.subtract(BigDecimal.valueOf(500000))
                            .multiply(BigDecimal.valueOf(0.20)));
        }
    }

    @Override
    public BigDecimal calculateProfTax(BigDecimal monthlySalary) {
        if (monthlySalary.compareTo(BigDecimal.valueOf(15000)) <= 0) {
            return BigDecimal.valueOf(200);
        } else {
            return BigDecimal.valueOf(300);
        }
    }

    @Override
    public EmployeeSalary createEmployeeSalary(EmployeeSalaryDTO salaryDTO) {
        Employee employee = employeeRepository.findById(salaryDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        EmployeeSalary salary = EmployeeSalary.builder()
                .employee(employee)
                .netSalary(salaryDTO.getNetSalary())
                .basicSalary(salaryDTO.getBasicSalary())
                .conveyance(salaryDTO.getConveyance())
                .hra(calculateHRA(salaryDTO.getBasicSalary()))
                .allowance(salaryDTO.getAllowance())
                .medicalAllowance(salaryDTO.getMedicalAllowance())
                .build();

        BigDecimal grossSalary = salary.getBasicSalary()
                .add(salary.getConveyance())
                .add(salary.getHra())
                .add(salary.getAllowance())
                .add(salary.getMedicalAllowance());
        salary.setGrossSalary(grossSalary);
        salary.setTds(calculateTDS(grossSalary.multiply(BigDecimal.valueOf(12))));
        salary.setProfTax(calculateProfTax(grossSalary));
        salary.setLeaveDeduction(salaryDTO.getLeaveDeduction());
        salary.setLabourWelfare(salaryDTO.getLabourWelfare());
        
        BigDecimal totalDeductions = salary.getTds()
                .add(salary.getProfTax())
                .add(salary.getLeaveDeduction())
                .add(salary.getLabourWelfare());
        salary.setTotalDeductions(totalDeductions);

        // Calculate final net salary
        salary.setFinalNetSalary(grossSalary.subtract(totalDeductions));
        salary.setEffectiveDate(LocalDate.now());
        salary.setStatus(SalaryStatus.ACTIVE);

        return employeeSalaryRepository.save(salary);

    }

    @Override
    public List<PayrollItemDTO> getPayrollItems() {
        List<PayrollItem> items = payrollItemRepository.findByIsActiveTrue();
        return converter.toDTOList(items);

    }

    @Override
    public EmployeeSalary getEmployeeSalaryByEmployeeId(Long employeeId) {
        return employeeSalaryRepository.findByEmployeeIdAndStatus(employeeId, SalaryStatus.ACTIVE)
                .orElseThrow(() -> new EntityNotFoundException("Active salary not found for employee ID: " + employeeId));
    }

    @Override
    public List<EmployeeSalary> getEmployeeSalaryHistory(Long employeeId) {
        return employeeSalaryRepository.findByEmployeeId(employeeId);
    }

    @Override
    public EmployeeSalary updateEmployeeSalary(Long salaryId, EmployeeSalaryDTO salaryDTO) {
        EmployeeSalary salary = employeeSalaryRepository.findById(salaryId)
                .orElseThrow(() -> new EntityNotFoundException("Salary not found"));

        salary.setBasicSalary(salaryDTO.getBasicSalary());
        salary.setConveyance(salaryDTO.getConveyance());
        salary.setHra(calculateHRA(salaryDTO.getBasicSalary()));
        salary.setAllowance(salaryDTO.getAllowance());
        salary.setMedicalAllowance(salaryDTO.getMedicalAllowance());

        // Recalculate gross salary
        BigDecimal grossSalary = salary.getBasicSalary()
                .add(salary.getConveyance())
                .add(salary.getHra())
                .add(salary.getAllowance())
                .add(salary.getMedicalAllowance());
        salary.setGrossSalary(grossSalary);

        // Update deductions
        salary.setTds(calculateTDS(grossSalary.multiply(BigDecimal.valueOf(12))));
        salary.setProfTax(calculateProfTax(grossSalary));
        salary.setLeaveDeduction(salaryDTO.getLeaveDeduction());
        salary.setLabourWelfare(salaryDTO.getLabourWelfare());

        // Recalculate total deductions
        BigDecimal totalDeductions = salary.getTds()
                .add(salary.getProfTax())
                .add(salary.getLeaveDeduction())
                .add(salary.getLabourWelfare());
        salary.setTotalDeductions(totalDeductions);

        // Recalculate final net salary
        salary.setFinalNetSalary(grossSalary.subtract(totalDeductions));

        return employeeSalaryRepository.save(salary);
    }

    @Override
    public void deleteEmployeeSalary(Long salaryId) {
        EmployeeSalary salary = employeeSalaryRepository.findById(salaryId)
                .orElseThrow(() -> new EntityNotFoundException("Salary not found"));
        salary.setStatus(SalaryStatus.INACTIVE);
        employeeSalaryRepository.save(salary);
    }

    @Override
    public PayrollItem createPayrollItem(PayrollItem item) {
        item.setIsActive(true);
        item.setAdditionDate(LocalDate.now());
        return payrollItemRepository.save(item);
    }

    @Override
    public PayrollItem updatePayrollItem(Long itemId, PayrollItem updatedItem) {
        PayrollItem item = payrollItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Payroll item not found"));

        item.setName(updatedItem.getName());
        item.setCategory(updatedItem.getCategory());
        item.setType(updatedItem.getType());
        item.setAmount(updatedItem.getAmount());
        item.setPercentage(updatedItem.getPercentage());

        return payrollItemRepository.save(item);
    }

    @Override
    public void deletePayrollItem(Long itemId) {
        PayrollItem item = payrollItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Payroll item not found"));
        item.setIsActive(false);
        payrollItemRepository.save(item);
    }

    @Override
    public List<PayrollItemDTO> getPayrollItemsByCategory(PayrollCategory category) {
        List<PayrollItem> items = payrollItemRepository.findByCategory(category);
        return converter.toDTOList(items);
    }
}
