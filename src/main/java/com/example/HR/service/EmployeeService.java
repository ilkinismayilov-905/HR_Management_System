package com.example.HR.service;

import com.example.HR.dto.EmployeeDTO;
import com.example.HR.entity.Employee;
import com.example.HR.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface EmployeeService extends GeneralService<EmployeeDTO,Long>{

}
