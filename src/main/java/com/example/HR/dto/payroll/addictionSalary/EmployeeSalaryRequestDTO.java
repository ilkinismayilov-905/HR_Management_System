package com.example.HR.dto.payroll.addictionSalary;

import com.example.HR.validation.Create;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeSalaryRequestDTO {
    @NotNull(message = "Employee ID is required",groups = Create.class)
    private Long employeeId;

    @NotNull(message = "Basic salary is required",groups = Create.class)
    @DecimalMin(value = "0.0", message = "Basic salary must be positive")
    private BigDecimal basicSalary;

    @DecimalMin(value = "0.0", message = "Conveyance must be positive")
    @NotNull(groups = Create.class)
    private BigDecimal conveyance;

    @DecimalMin(value = "0.0", message = "Allowance must be positive")
    @NotNull(groups = Create.class)
    private BigDecimal allowance ;

    @DecimalMin(value = "0.0", message = "Medical allowance must be positive")
    @NotNull(groups = Create.class)
    private BigDecimal medicalAllowance ;

    @DecimalMin(value = "0.0", message = "Leave deduction cannot be negative")
    @NotNull(groups = Create.class)
    private BigDecimal leaveDeduction;

    @DecimalMin(value = "0.0", message = "Labour welfare cannot be negative")
    @NotNull(groups = Create.class)
    private BigDecimal labourWelfare ;
}
