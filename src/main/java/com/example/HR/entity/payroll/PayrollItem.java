package com.example.HR.entity.payroll;

import com.example.HR.enums.PayrollCategory;
import com.example.HR.enums.PayrollType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payroll_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private PayrollCategory category; // ADDITION, OVERTIME, DEDUCTION

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PayrollType type;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "percentage")
    private BigDecimal percentage;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "addition_date")
    private LocalDate additionDate;
}
