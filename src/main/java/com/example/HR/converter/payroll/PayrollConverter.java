package com.example.HR.converter.payroll;

import com.example.HR.dto.payroll.*;

import com.example.HR.dto.payroll.addictionSalary.EmployeeSalaryResponseDTO;
import com.example.HR.entity.payroll.EmployeeSalary;
import com.example.HR.entity.payroll.PayrollItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PayrollConverter {
    public PayrollItemDTO convertToDTO(PayrollItem item) {
        return PayrollItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .category(item.getCategory())
                .amount(item.getAmount())
                .additionDate(item.getAdditionDate())
                .build();
    }

    public List<PayrollItemDTO> toDTOList(List<PayrollItem> list){
        return list.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EmployeeSalaryResponseDTO convertToResponseDTO(EmployeeSalary salary) {
        EmployeeSalaryResponseDTO dto = new EmployeeSalaryResponseDTO();
        dto.setId(salary.getId());
        dto.setEmployeeId(salary.getEmployee().getId());
        dto.setEmployeeName(salary.getEmployee().getFullname().getFullname());

        // Earnings
        dto.setBasicSalary(salary.getBasicSalary());
        dto.setConveyance(salary.getConveyance());
        dto.setHra(salary.getHra());
        dto.setAllowance(salary.getAllowance());
        dto.setMedicalAllowance(salary.getMedicalAllowance());
        dto.setOvertimeAmount(salary.getOvertimeAmount());
        dto.setBonus(salary.getBonus());

        // Deductions
        dto.setTds(salary.getTds());
        dto.setLeaveDeduction(salary.getLeaveDeduction());
        dto.setProfTax(salary.getProfTax());
        dto.setLabourWelfare(salary.getLabourWelfare());
        dto.setProvidentFund(salary.getProvidentFund());

        // Calculated fields
        dto.setGrossSalary(salary.getGrossSalary());
        dto.setTotalDeductions(salary.getTotalDeductions());
        dto.setFinalNetSalary(salary.getFinalNetSalary());

        dto.setEffectiveDate(salary.getEffectiveDate());
        dto.setStatus(salary.getStatus().toString());

        return dto;
    }

    public List<EmployeeSalaryResponseDTO> convertToResponseDTOList(List<EmployeeSalary> list){
        return list.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

}
