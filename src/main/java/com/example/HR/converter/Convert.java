package com.example.HR.converter;

import com.example.HR.dto.EmployeeDTO;
import com.example.HR.entity.User;
import com.example.HR.entity.employee.Employee;
import com.example.HR.enums.Status;
import com.example.HR.exception.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Convert {
    public static Employee dtoToEntity(EmployeeDTO dto) {
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
        employee.setStatus(dto.getStatus() != null ? dto.getStatus() : Status.ACTIVE); // default olaraq ACTIVE

//       User user = userRepository.findByUsername(dto.getUsername())
//        .orElseThrow(() -> new NotFoundException("User not found: " + dto.getUsername()));
//
//    User emailUser = userRepository.findByEmail(dto.getEmail())
//        .orElseThrow(() -> new NotFoundException("User not found by email: " + dto.getEmail()));
//
//    employee.setUserName(user);
//    employee.setEmail(emailUser);
//    employee.setPassword(user);

        return employee;
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
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setCompany(employee.getCompany());
        dto.setDepartament(employee.getDepartament());
        dto.setJobTitle(employee.getJobTitle());
        dto.setAbout(employee.getAbout());
        dto.setEmploymentType(employee.getEmploymentType());
        dto.setStatus(employee.getStatus());

        // User əlaqələri — sadəcə String olaraq
        if (employee.getUsername() != null)
            dto.setUsername(employee.getUsername().getUsername());

        if (employee.getEmail() != null)
            dto.setEmail(employee.getEmail().getEmail());

        if (employee.getPassword() != null)
            dto.setPassword(employee.getPassword().getPassword());

        if (employee.getConfirmPassword() != null)
            dto.setConfirmPassword(employee.getConfirmPassword().getPassword());

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
