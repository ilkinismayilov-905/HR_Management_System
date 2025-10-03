package com.example.HR.repository.employee;

import com.example.HR.entity.User;
import com.example.HR.entity.employee.Employee;
import com.example.HR.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    public List<Employee> getEmployeeByStatus(Status status);
    public List<Employee> getEmployeeByJobTitle(JobTitle jobTitle);
    public List<Employee> getEmployeeByEmploymentType(EmploymentType employmentType);
    public List<Employee> getEmployeeByJoinDate(LocalDate localDate);
    public Optional<Employee> findByFullnameFullname(String fullname);
    public Employee findByEmail(User email);
    public Employee findByPassword(User password);
    public Optional<Employee> findByEmployeeId(String employeeId);
}
