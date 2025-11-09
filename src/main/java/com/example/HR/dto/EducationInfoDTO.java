package com.example.HR.dto;

import com.example.HR.customAnnotation.YearOnly;
import com.example.HR.enums.Major;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationInfoDTO {

    @PositiveOrZero(message = "ID cannot be negative")
    private Long id;

    @NotBlank(message = "University name is required")
    @Size(max = 255, message = "University name must not exceed 255 characters")
    private String universityName;

    @NotNull(message = "Major is required")
    private Major major;

    @NotNull(message = "Start date is required")
    @Pattern(regexp = "\\d{4}", message = "Year must be a 4-digit number")
    private String startDate;

    @Pattern(regexp = "\\d{4}", message = "Year must be a 4-digit number")
    private String endDate;
}
