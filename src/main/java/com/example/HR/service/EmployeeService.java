package com.example.HR.service;

import com.example.HR.dto.EmployeeInformationDTO;
import com.example.HR.dto.employee.EmployeeRequestDTO;
import com.example.HR.dto.employee.EmployeeResponseDTO;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface EmployeeService{
    public List<EmployeeResponseDTO> getByStatus(Status status);
    public List<EmployeeResponseDTO> getByJobPosition(JobTitle jobTitle);
    public List<EmployeeResponseDTO> getByEmploymentType(EmploymentType employmentType);
    public Optional<EmployeeResponseDTO> getByFullname(String fullName);
    public List<EmployeeResponseDTO> getByDate(LocalDate localDate);
    public List<EmployeeResponseDTO> findAll();
    Optional<EmployeeInformationDTO> getByEmployeeID(String employeeID);
    public EmployeeRequestDTO save(EmployeeRequestDTO employeeRequestDTO) throws IOException;
    public EmployeeRequestDTO update(Long id, EmployeeRequestDTO updatedDto);
    public Optional<EmployeeResponseDTO> getById(Long id);
    public void deleteById(Long id);


}
