package com.example.HR.converter.payroll;

import com.example.HR.dto.payroll.addiction.AdditionRequestDTO;
import com.example.HR.dto.payroll.addiction.AdditionResponseDTO;
import com.example.HR.entity.payroll.Addition;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddictionConverter {

    public AdditionResponseDTO toResponseAddition(Addition addition) {
        return AdditionResponseDTO.builder()
                .id(addition.getId())
                .name(addition.getName())
                .category(addition.getCategory())
                .amount(addition.getAmount())
                .additionDate(addition.getAdditionDate())
                .salary(addition.getSalary())
                .build();
    }

    public List<AdditionResponseDTO> toResponseAdditionList(List<Addition> list) {
        return list.stream()
                .map(this::toResponseAddition)
                .collect(Collectors.toList());
    }

    public Addition toEntityAddition(AdditionRequestDTO dto){
        return Addition.builder()
                .name(dto.getName())
                .category(dto.getCategory())
                .amount(dto.getAmount())
                .additionDate(LocalDate.now())
                .unitCalculation(dto.isUnitCalculation())
                .assigneeType(dto.getAssigneeType())
                .build();
    }

    public void updateAddition(Addition entity, AdditionRequestDTO dto) {
        if(entity == null || dto == null) return;
        if(dto.getName() != null ) entity.setName(dto.getName());
        if(dto.getCategory() != null ) entity.setCategory(dto.getCategory());
        if(dto.getAmount() != null ) entity.setAmount(dto.getAmount());
        entity.setUnitCalculation(dto.isUnitCalculation());
        if(dto.getAssigneeType() != null ) entity.setAssigneeType(dto.getAssigneeType());

    }
}
