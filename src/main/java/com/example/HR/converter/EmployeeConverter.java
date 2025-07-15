package com.example.HR.converter;

import com.example.HR.dto.EmployeeDTO;
import com.example.HR.entity.employee.Employee;
import com.example.HR.enums.Status;

public class EmployeeConverter extends Convert<EmployeeDTO, Employee> {
    @Override
    public Employee dtoToEntity(EmployeeDTO dto) {
        if (dto == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setFullname(dto.getFullname());
        employee.setEmployeeId(dto.getEmployeeId());
        employee.setJoinDate(dto.getJoinDate());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setCompany(dto.getCompany());
        employee.setDepartament(dto.getDepartament());
        employee.setJobTitle(dto.getJobTitle());
        employee.setAbout(dto.getAbout());
        employee.setEmploymentType(dto.getEmploymentType());
        employee.setStatus(dto.getStatus() != null ? dto.getStatus() : Status.ACTIVE); // default as ACTIVE
        // Note: User relationships should be set in the service layer
        return employee;
    }

    @Override
    public EmployeeDTO entityToDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeDTO dto = new EmployeeDTO();
        dto.setFullname(employee.getFullname());
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setJoinDate(employee.getJoinDate());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setCompany(employee.getCompany());
        dto.setDepartament(employee.getDepartament());
        dto.setJobTitle(employee.getJobTitle());
        dto.setAbout(employee.getAbout());
        dto.setEmploymentType(employee.getEmploymentType());
        dto.setStatus(employee.getStatus());
        // User relationships — convert to String
        if (employee.getUsername() != null) {
            dto.setUsername(employee.getUsername().getUsername());
        }
        if (employee.getEmail() != null) {
            dto.setEmail(employee.getEmail().getEmail());
        }
        if (employee.getPassword() != null) {
            dto.setPassword(employee.getPassword().getPassword());
        }
        if (employee.getConfirmPassword() != null) {
            dto.setConfirmPassword(employee.getConfirmPassword().getConfirmPassword());
        }
        return dto;
    }
} 