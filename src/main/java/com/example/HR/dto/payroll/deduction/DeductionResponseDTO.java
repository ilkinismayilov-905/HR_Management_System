package com.example.HR.dto.payroll.deduction;

import com.example.HR.enums.payroll.DeductionRate;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeductionResponseDTO {
    private Long id;

    private String name;

    private String salary;

    private DeductionRate rate;

    private LocalDate createdDate;

}
