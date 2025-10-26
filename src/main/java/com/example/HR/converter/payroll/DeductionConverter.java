package com.example.HR.converter.payroll;

import com.example.HR.dto.payroll.deduction.DeductionRequestDTO;
import com.example.HR.dto.payroll.deduction.DeductionResponseDTO;
import com.example.HR.entity.payroll.Deduction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeductionConverter {

    public DeductionResponseDTO toResponseDeduction(Deduction deduction) {
        return DeductionResponseDTO.builder()
                .id(deduction.getId())
                .name(deduction.getName())
                .salary(deduction.getSalary())
                .rate(deduction.getRate())
                .createdDate(deduction.getCreatedDate())
                .build();
    }

    public List<DeductionResponseDTO> toResponseDeductionList(List<Deduction> list) {
        return list.stream()
                .map(this::toResponseDeduction)
                .collect(Collectors.toList());
    }

    public Deduction toEntityDeduction(DeductionRequestDTO dto){
        return Deduction.builder()
                .name(dto.getName())
                .amount(dto.getAmount())
                .salary(dto.getSalary())
                .rate(dto.getRate())
                .build();
    }

    public void updateDeduction(Deduction entity, DeductionRequestDTO dto) {
        if(entity == null || dto == null) return;
        if(dto.getId() != null ) entity.setId(dto.getId());
        if(dto.getName() != null ) entity.setName(dto.getName());
        if(dto.getSalary() != null ) entity.setSalary(dto.getSalary());
        if(dto.getRate() != null ) entity.setRate(dto.getRate());

    }

}
