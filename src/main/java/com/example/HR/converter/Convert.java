package com.example.HR.converter;

import com.example.HR.dto.EmployeeDTO;
import com.example.HR.entity.Employee;

public class Convert {
    public static Employee dtoToEntity(EmployeeDTO dto) {
        if (dto == null) {
            return null;
        }

        Employee entity = new Employee();
        entity.setFullname(dto.getFullname());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setJobPosition(dto.getJobPosition());
        entity.setJoinDate(dto.getJoinDate());
        entity.setEmploymentType(dto.getEmploymentType());

        return entity;
    }

    public static EmployeeDTO entityToDto(Employee entity) {
        if (entity == null) {
            return null;
        }

        EmployeeDTO dto = new EmployeeDTO();
        dto.setFullname(entity.getFullname());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setJobPosition(entity.getJobPosition());
        dto.setJoinDate(entity.getJoinDate());
        dto.setEmploymentType(entity.getEmploymentType());

        return dto;
    }
}
