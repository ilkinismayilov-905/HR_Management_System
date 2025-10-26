package com.example.HR.repository.payroll;

import com.example.HR.entity.payroll.Addition;
import com.example.HR.entity.payroll.Deduction;
import com.example.HR.enums.payroll.DeductionRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DeductionRepository extends JpaRepository<Deduction, Long> {
    List<Deduction> findByCreatedDateBetween(LocalDate createdDateAfter, LocalDate createdDateBefore);

    List<Deduction> findByRate(DeductionRate rate);
}
