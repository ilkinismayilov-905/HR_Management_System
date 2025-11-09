package com.example.HR.repository.payroll;

import com.example.HR.entity.payroll.Overtime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OvertimeRepository extends JpaRepository<Overtime, Long> {
    List<Overtime> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate);
}
