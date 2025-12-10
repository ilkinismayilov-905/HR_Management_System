package com.example.HR.service;

import com.example.HR.dto.EmployeeInformationDTO;
import com.example.HR.dto.employee.EmployeeRequestDTO;
import com.example.HR.dto.employee.EmployeeResponseDTO;
import com.example.HR.entity.employee.EmployeeAttachment;
import com.example.HR.entity.ticket.TicketAttachment;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface EmployeeService{
    public List<EmployeeResponseDTO> getByStatus(Status status);
    public List<EmployeeResponseDTO> getByJobPosition(JobTitle jobTitle);
    public List<EmployeeResponseDTO> getByEmploymentType(EmploymentType employmentType);
    public EmployeeResponseDTO getByFullname(String fullName);
    public List<EmployeeResponseDTO> getByDate(LocalDate localDate);
    public List<EmployeeResponseDTO> findAll();
    public EmployeeInformationDTO getByEmployeeID(String employeeID);
    public EmployeeResponseDTO save(EmployeeRequestDTO employeeRequestDTO) throws IOException;
    public EmployeeResponseDTO update(Long id, EmployeeRequestDTO updatedDto);
    public EmployeeResponseDTO getById(Long id);
    EmployeeAttachment uploadAttachment(String employeeID, MultipartFile file);
    public void deleteById(Long id);
    public Long countEmployees();


}
