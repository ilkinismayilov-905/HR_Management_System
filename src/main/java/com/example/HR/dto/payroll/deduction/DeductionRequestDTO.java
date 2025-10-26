package com.example.HR.dto.payroll.deduction;

import com.example.HR.enums.payroll.DeductionRate;
import com.example.HR.validation.Create;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.http.parser.Upgrade;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeductionRequestDTO {
    @NotNull(groups = Upgrade.class, message = "Id is required for update")
    private Long id;

    @NotBlank(groups = Create.class)
    private String name;

    @NotNull(groups = Create.class)
    private String amount;

    private DeductionRate rate;
    private String salary;
}
