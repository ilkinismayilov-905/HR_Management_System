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
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setJoinDate(dto.getJoinDate());
        entity.setUserName(dto.getUserName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setCompany(dto.getCompany());
        entity.setDepartament(dto.getDepartament());
        entity.setJobTitle(dto.getJobTitle());
        entity.setAbout(dto.getAbout());
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


    public static EmployeeDTO entityToDto(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeDTO dto = new EmployeeDTO();
        dto.setFullname(employee.getFullname());
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setJoinDate(employee.getJoinDate());
        dto.setUserName(employee.getUserName());
        dto.setEmail(employee.getEmail());
        dto.setPassword(employee.getPassword());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setCompany(employee.getCompany());
        dto.setDepartament(employee.getDepartament());
        dto.setJobTitle(employee.getJobTitle());
        dto.setAbout(employee.getAbout());
        dto.setEmploymentType(employee.getEmploymentType());

        // confirmPassword frontenddə input olaraq gəlir, bazaya yazılmır
//        dto.setConfirmPassword(""); // və ya null

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
