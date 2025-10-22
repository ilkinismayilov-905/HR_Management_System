package com.example.HR.repository.payroll;

import com.example.HR.entity.payroll.Overtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OvertimeRepository extends JpaRepository<Overtime, Long> {
}
