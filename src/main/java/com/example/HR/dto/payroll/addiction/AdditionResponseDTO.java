package com.example.HR.dto.payroll.addiction;
import com.example.HR.enums.payroll.AdditionCategory;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdditionResponseDTO {
    private Long id;
    private String name;
    private AdditionCategory category;
    private String amount;
    private LocalDate additionDate;
    private BigDecimal salary;
}
