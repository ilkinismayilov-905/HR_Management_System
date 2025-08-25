package com.example.HR.entity.employee;


import com.example.HR.customAnnotation.YearOnly;
import com.example.HR.enums.Major;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "education_information")
@AllArgsConstructor
@NoArgsConstructor
public class EducationInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "University name is required")
    @Column(name = "university_name", nullable = false)
    private String universityName;

    @NotNull(message = "Field of Study is required")
    @Column(name = "major", nullable = false)
    @Enumerated(EnumType.STRING)
    private Major major;

    @NotNull(message = "Start date is required")
    @Column(name = "start_date",nullable = false)
    @Pattern(regexp = "\\d{4}", message = "Year must be a 4-digit number")
    private String startDate;

    @NotNull(message = "End date is required")
    @Column(name = "end_date",nullable = false)
    @Pattern(regexp = "\\d{4}", message = "Year must be a 4-digit number")
    private String endDate;
}
