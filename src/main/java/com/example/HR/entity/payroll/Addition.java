package com.example.HR.entity.payroll;

import com.example.HR.enums.payroll.AdditionCategory;
import com.example.HR.enums.payroll.AssigneeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "additions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Addition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private AdditionCategory category;


    @Column(nullable = false, scale = 2, precision = 19)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(name = "addition_date", nullable = false)
    private LocalDate additionDate;

    @Column(name = "salary", scale = 2, precision = 19)
    private BigDecimal salary;

    @Column(name = "unit_calculation", nullable = false)
    private boolean unitCalculation;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignee_type", nullable = false)
    private AssigneeType assigneeType;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
