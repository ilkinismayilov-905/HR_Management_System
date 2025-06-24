package com.example.HR.converter;

import com.example.HR.dto.EmployeeDTO;
import com.example.HR.entity.employee.Employee;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Convert {
    public static Employee dtoToEntity(EmployeeDTO dto) {
        if (dto == null) {
            return null;
        }

        Employee entity = new Employee();
        entity.setFullname(dto.getFullname());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setJobTitle(dto.getJobTitle());
        entity.setJoinDate(dto.getJoinDate());
        entity.setEmploymentType(dto.getEmploymentType());

        return entity;
    }

    public static List<Employee> dtoListToEntityList(List<EmployeeDTO> dtoList) {
        if (dtoList == null) {
            return Collections.emptyList();
        }

        return dtoList.stream()
                .map(Convert::dtoToEntity)
                .collect(Collectors.toList());
    }


    public static EmployeeDTO entityToDto(Employee entity) {
        if (entity == null) {
            return null;
        }

        EmployeeDTO dto = new EmployeeDTO();
        dto.setFullname(entity.getFullname());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setJobTitle(entity.getJobTitle());
        dto.setJoinDate(entity.getJoinDate());
        dto.setEmploymentType(entity.getEmploymentType());

        return dto;
    }

    public static List<EmployeeDTO> entityListToDtoList(List<Employee> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(Convert::entityToDto)
                .collect(Collectors.toList());
    }

}
