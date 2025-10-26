package com.example.HR.converter.payroll;

import com.example.HR.dto.payroll.overtime.OvertimeRequestDTO;
import com.example.HR.dto.payroll.overtime.OvertimeResponseDTO;
import com.example.HR.entity.payroll.Overtime;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OvertimeConverter {
    public OvertimeResponseDTO toResponseOvertime(Overtime overtime) {
        return OvertimeResponseDTO.builder()
                .id(overtime.getId())
                .name(overtime.getName())
                .salary(overtime.getSalary())
                .rate(overtime.getRate())
                .otNumber(overtime.getOtNumber())
                .createdDate(overtime.getCreatedDate())
                .build();
    }

    public List<OvertimeResponseDTO> toResponseOvertimeList(List<Overtime> list) {
        return list.stream()
                .map(this::toResponseOvertime)
                .collect(Collectors.toList());
    }

    public Overtime toEntityOvertime(OvertimeRequestDTO dto){
        return Overtime.builder()
                .name(dto.getName())
                .salary(dto.getSalary())
                .rate(dto.getRateValue())
                .otNumber(dto.getOtNumberValue())
                .build();
    }

    public void updateOvertime(Overtime entity, OvertimeRequestDTO dto) {
        if(entity == null || dto == null) return;
        if(dto.getId() != null ) entity.setId(dto.getId());
        if(dto.getName() != null ) entity.setName(dto.getName());
        if(dto.getSalary() != null ) entity.setSalary(dto.getSalary());
        if(dto.getRateValue() != null ) entity.setRate(dto.getRateValue());
        if(dto.getOtNumberValue() != null ) entity.setOtNumber(dto.getOtNumberValue());

    }


}
