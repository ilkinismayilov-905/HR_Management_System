package com.example.HR.repository;

import com.example.HR.entity.employee.Employee;
import com.example.HR.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    public List<Employee> getEmployeeByStatus(Status status);
    public List<Employee> getEmployeeByJobPosition(JobTitle jobTitle);
    public List<Employee> getEmployeeByFullname(String fullName);
    public List<Employee> getEmployeeByEmploymentType(EmploymentType employmentType);
    public List<Employee> getEmployeeByDate(LocalDate localDate);
}
