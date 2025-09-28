package com.example.HR.repository.payroll;

import com.example.HR.entity.payroll.MonthlyPayroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyPayrollRepository extends JpaRepository<MonthlyPayroll, Long> {
    List<MonthlyPayroll> findByEmployeeIdAndMonthAndYear(Long employeeId, Integer month, Integer year);
    List<MonthlyPayroll> findByMonthAndYear(Integer month, Integer year);
}
