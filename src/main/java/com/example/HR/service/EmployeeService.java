package com.example.HR.service;

import com.example.HR.dto.EmployeeRequestDTO;
import com.example.HR.dto.EmployeeResponseDTO;
import com.example.HR.dto.UserResponseDTO;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface EmployeeService extends GeneralService<EmployeeRequestDTO,Long>{
    public List<EmployeeRequestDTO> getByStatus(Status status);
    public List<EmployeeRequestDTO> getByJobPosition(JobTitle jobTitle);
    public List<EmployeeRequestDTO> getByEmploymentType(EmploymentType employmentType);
    public Optional<EmployeeRequestDTO> getByFullname(String fullName);
    public List<EmployeeRequestDTO> getByDate(LocalDate localDate);
    public List<EmployeeResponseDTO> findAll();


}
