package com.example.HR.service;

import com.example.HR.dto.EmployeeDTO;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;

import java.time.LocalDate;
import java.util.List;


public interface EmployeeService extends GeneralService<EmployeeDTO,Long>{
    public List<EmployeeDTO> getByStatus(Status status);
    public List<EmployeeDTO> getByJobPosition(JobTitle jobTitle);
    public List<EmployeeDTO> getByEmploymentType(EmploymentType employmentType);
    public List<EmployeeDTO> getByFulName(String fullName);
    public List<EmployeeDTO> getByDate(LocalDate localDate);


}
