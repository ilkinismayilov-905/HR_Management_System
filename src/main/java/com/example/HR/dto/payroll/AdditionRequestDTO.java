package com.example.HR.dto.payroll;

import com.example.HR.enums.payroll.AdditionCategory;
import com.example.HR.enums.payroll.AssigneeType;
import com.example.HR.validation.Create;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.apache.tomcat.util.http.parser.Upgrade;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdditionRequestDTO {
    @NotNull(groups = Upgrade.class, message = "Id is required for update")
    private Long id;

    @NotBlank(groups = Create.class)
    private String name;

    @NotNull(groups = Create.class)
    private AdditionCategory category;

    @NotNull(groups = Create.class)
    private String amount;

    // unitCalculation toggle
    private boolean unitCalculation;

    @Enumerated(EnumType.STRING)
    @NotNull(groups = Create.class)
    private AssigneeType assigneeType;
}
