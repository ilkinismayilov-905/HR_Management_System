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
        employee.setId(dto.getId());
        employee.setEmployeeId(dto.getEmployeeId());
        employee.setJoinDate(dto.getJoinDate());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setCompany(dto.getCompany());
        employee.setDepartament(dto.getDepartament());
        employee.setJobTitle(dto.getJobTitle());
        employee.setAbout(dto.getAbout());
        employee.setEmploymentType(dto.getEmploymentType());
        employee.setStatus(dto.getStatus() != null ? dto.getStatus() : Status.ACTIVE);// default as ACTIVE
//        employee.setImageType(dto.getImageType());
//        employee.setImageName(dto.getImageName());
//        employee.setImageDate(dto.getImageDate());
        // Note: User relationships should be set in the service layer
        return employee;
    }

    @Override
    public EmployeeDTO entityToDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setJoinDate(employee.getJoinDate());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setCompany(employee.getCompany());
        dto.setDepartament(employee.getDepartament());
        dto.setJobTitle(employee.getJobTitle());
        dto.setAbout(employee.getAbout());
        dto.setEmploymentType(employee.getEmploymentType());
        dto.setStatus(employee.getStatus());
//        dto.setImageType(employee.getImageType());
//        dto.setImageName(employee.getImageName());
//        dto.setImageDate(employee.getImageDate());
        // User relationships — convert to String
        if (employee.getFullname() != null) {
            dto.setFullname(employee.getFullname().getFullname());
        }
        if (employee.getEmail() != null) {
            dto.setEmail(employee.getEmail().getEmail());
        }
        if (employee.getPassword() != null) {
            dto.setPassword(employee.getPassword().getPassword());
        }
        return dto;
    }

    /**
     * Updates the given Employee entity with non-null values from the EmployeeDTO.
     * User relationships are updated only if the User object is not null.
     * This does not create new User objects.
     */
    public void updateEntityFromDto(EmployeeDTO dto, Employee entity) {
        if (dto == null || entity == null) {
            return;
        }
        if(dto.getId() != null) entity.setId(dto.getId());
        if (dto.getEmployeeId() != null) entity.setEmployeeId(dto.getEmployeeId());
        if (dto.getJoinDate() != null) entity.setJoinDate(dto.getJoinDate());
        if (dto.getPhoneNumber() != null) entity.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getCompany() != null) entity.setCompany(dto.getCompany());
        if (dto.getDepartament() != null) entity.setDepartament(dto.getDepartament());
        if (dto.getJobTitle() != null) entity.setJobTitle(dto.getJobTitle());
        if (dto.getAbout() != null) entity.setAbout(dto.getAbout());
        if (dto.getEmploymentType() != null) entity.setEmploymentType(dto.getEmploymentType());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        // User relationships — update fields if User object exists
        if (dto.getFullname() != null && entity.getFullname() != null) {
            entity.getFullname().setFullname(dto.getFullname());
        }
        if (dto.getEmail() != null && entity.getEmail() != null) {
            entity.getEmail().setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null && entity.getPassword() != null) {
            entity.getPassword().setPassword(dto.getPassword());
        }
    }
} 