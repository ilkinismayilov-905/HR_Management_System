package com.example.HR.dto;

import com.example.HR.entity.employee.Employee;
import com.example.HR.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class PersonalInformationDTO {

    private Employee employee;

    private LocalDate birthDate;

    private String nationality;

    private String address;

    private Gender gender;

    private String postalCode;

    private String emergencyContact;

    private String passportNo;

}
