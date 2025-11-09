package com.example.HR.repository.payroll;

import com.example.HR.entity.payroll.PayrollItem;
import com.example.HR.enums.payroll.PayrollCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollItemRepository  extends JpaRepository<PayrollItem, Long> {
    List<PayrollItem> findByIsActiveTrue();
    List<PayrollItem> findByCategory(PayrollCategory category);

}
