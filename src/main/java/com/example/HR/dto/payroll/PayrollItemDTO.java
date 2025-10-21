package com.example.HR.dto.payroll;

import com.example.HR.enums.payroll.PayrollCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayrollItemDTO {
    private Long id;
    private String name;
    private PayrollCategory category;
    private BigDecimal amount;
    private LocalDate additionDate;
    private BigDecimal salary;
}
