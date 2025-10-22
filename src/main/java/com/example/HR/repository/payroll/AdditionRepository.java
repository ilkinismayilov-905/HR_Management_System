package com.example.HR.repository.payroll;

import com.example.HR.entity.Calendar;
import com.example.HR.entity.payroll.Addition;
import com.example.HR.enums.payroll.AdditionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdditionRepository extends JpaRepository<Addition,Long> {
    List<Addition> findByCategory(AdditionCategory category);
    List<Addition> findByAdditionDateBetween(LocalDate startDate, LocalDate endDate);

}
