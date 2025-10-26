package com.example.HR.service.implement;

import com.example.HR.converter.payroll.PayrollConverter;
import com.example.HR.dto.payroll.addictionSalary.EmployeeSalaryRequestDTO;
import com.example.HR.dto.payroll.addictionSalary.EmployeeSalaryResponseDTO;
import com.example.HR.dto.payroll.PayrollItemDTO;
import com.example.HR.entity.employee.Employee;
import com.example.HR.entity.payroll.EmployeeSalary;
import com.example.HR.entity.payroll.PayrollItem;
import com.example.HR.enums.payroll.PayrollCategory;
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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

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
        return basicSalary.multiply(BigDecimal.valueOf(0.15))
                .setScale(2, RoundingMode.HALF_UP);
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
    public EmployeeSalaryResponseDTO createEmployeeSalary(EmployeeSalaryRequestDTO requestDTO) {
        Employee employee = employeeRepository.findById(requestDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));


        EmployeeSalary salary = new EmployeeSalary();
        salary.setEmployee(employee);

        // Set earnings
        salary.setBasicSalary(requestDTO.getBasicSalary());
        salary.setConveyance(requestDTO.getConveyance());
        salary.setHra(calculateHRA(requestDTO.getBasicSalary()));
        salary.setAllowance(requestDTO.getAllowance());
        salary.setMedicalAllowance(requestDTO.getMedicalAllowance());

        // Calculate gross salary
        BigDecimal grossSalary = salary.getBasicSalary()
                .add(salary.getConveyance())
                .add(salary.getHra())
                .add(salary.getAllowance())
                .add(salary.getMedicalAllowance());
        salary.setGrossSalary(grossSalary);

        // Set deductions
        BigDecimal annualIncome = grossSalary.multiply(BigDecimal.valueOf(12))
                .setScale(2, RoundingMode.HALF_UP);
        salary.setTds(calculateTDS(annualIncome).divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP));
        salary.setProfTax(calculateProfTax(grossSalary));
        salary.setLeaveDeduction(requestDTO.getLeaveDeduction());
        salary.setLabourWelfare(requestDTO.getLabourWelfare());

        // Calculate total deductions
        BigDecimal totalDeductions = salary.getTds()
                .add(salary.getProfTax())
                .add(salary.getLeaveDeduction())
                .add(salary.getLabourWelfare());
        salary.setTotalDeductions(totalDeductions);

        // Calculate final net salary
        salary.setFinalNetSalary(grossSalary.subtract(totalDeductions));
        salary.setEffectiveDate(LocalDate.now());
        salary.setStatus(SalaryStatus.ACTIVE);

        EmployeeSalary savedSalary = employeeSalaryRepository.save(salary);

        return converter.convertToResponseDTO(savedSalary);
    }

    @Override
    public List<PayrollItemDTO> getPayrollItems() {
        List<PayrollItem> items = payrollItemRepository.findByIsActiveTrue();
        return converter.toDTOList(items);

    }

    @Override
    public EmployeeSalaryResponseDTO getEmployeeSalaryByEmployeeId(Long employeeId) {
        EmployeeSalary salary = employeeSalaryRepository.findByEmployeeIdAndStatus(employeeId, SalaryStatus.ACTIVE)
                .orElseThrow(() -> new EntityNotFoundException("Active salary not found for employee ID: " + employeeId));
        return converter.convertToResponseDTO(salary);
    }

    @Override
    public List<EmployeeSalaryResponseDTO> getEmployeeSalaryHistory(Long employeeId) {
        List<EmployeeSalary> salaries = employeeSalaryRepository.findByEmployeeId(employeeId);
        return converter.convertToResponseDTOList(salaries);
    }

    @Override
    public EmployeeSalaryResponseDTO updateEmployeeSalary(Long salaryId, EmployeeSalaryRequestDTO requestDTO) {
        if(requestDTO == null) return null;

        EmployeeSalary salary = employeeSalaryRepository.findById(salaryId)
                .orElseThrow(() -> new EntityNotFoundException("Salary not found"));

        // Update earnings
        if(requestDTO.getBasicSalary() != null ) salary.setBasicSalary(requestDTO.getBasicSalary());
        if(requestDTO.getConveyance() != null ) salary.setConveyance(requestDTO.getConveyance());
        if(requestDTO.getBasicSalary() != null){
            salary.setHra(calculateHRA(requestDTO.getBasicSalary()));}
        else {
            salary.setHra(calculateHRA(salary.getBasicSalary()));
        }
        if(requestDTO.getAllowance() != null) salary.setAllowance(requestDTO.getAllowance());
        if(requestDTO.getMedicalAllowance() != null) salary.setMedicalAllowance(requestDTO.getMedicalAllowance());

        // Recalculate gross salary
        BigDecimal grossSalary = salary.getBasicSalary()
                .add(salary.getConveyance())
                .add(salary.getHra())
                .add(salary.getAllowance())
                .add(salary.getMedicalAllowance());
        salary.setGrossSalary(grossSalary);

        // Update deductions
        BigDecimal annualIncome = grossSalary.multiply(BigDecimal.valueOf(12))
                .setScale(2, RoundingMode.HALF_UP);
        salary.setTds(calculateTDS(annualIncome).divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP));
        salary.setProfTax(calculateProfTax(grossSalary));
        if(requestDTO.getLeaveDeduction() != null) salary.setLeaveDeduction(requestDTO.getLeaveDeduction());
        if(requestDTO.getLabourWelfare() != null) salary.setLabourWelfare(requestDTO.getLabourWelfare());

        // Recalculate total deductions
        BigDecimal totalDeductions = salary.getTds()
                .add(salary.getProfTax())
                .add(salary.getLeaveDeduction())
                .add(salary.getLabourWelfare());
        salary.setTotalDeductions(totalDeductions);

        // Recalculate final net salary
        salary.setFinalNetSalary(grossSalary.subtract(totalDeductions));

        EmployeeSalary updatedSalary = employeeSalaryRepository.save(salary);
        return converter.convertToResponseDTO(updatedSalary);
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
