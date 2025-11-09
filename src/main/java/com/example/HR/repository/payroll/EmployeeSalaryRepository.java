package com.example.HR.repository.payroll;

import com.example.HR.entity.payroll.EmployeeSalary;
import com.example.HR.enums.SalaryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary, Long> {
    Optional<EmployeeSalary> findByEmployeeIdAndStatus(Long employeeId, SalaryStatus status);
    List<EmployeeSalary> findByEmployeeId(Long employeeId);
}
