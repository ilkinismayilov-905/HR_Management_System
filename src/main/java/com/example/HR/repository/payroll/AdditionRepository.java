package com.example.HR.repository.payroll;

import com.example.HR.entity.payroll.Addition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionRepository extends JpaRepository<Addition,Long> {

}
