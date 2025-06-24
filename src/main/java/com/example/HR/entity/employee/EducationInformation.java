package com.example.HR.entity.employee;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Field of Study is required")
    @Column(name = "field_of_study", nullable = false)
    private String fieldOfStudy;

    @NotBlank(message = "Start date is required")
    @Column(name = "start_date",nullable = false)
    private Long startDate;

    @NotBlank(message = "End date is required")
    @Column(name = "end_date",nullable = false)
    private Long endDate;
}
