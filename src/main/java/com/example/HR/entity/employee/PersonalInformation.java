package com.example.HR.entity.employee;

import com.example.HR.entity.user.User;
import com.example.HR.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "personal_information")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersonalInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
//    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private Employee employee;

    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Past(message = "Date of birth must be in the past")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotBlank(message = "Nationality is required")
    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotBlank(message = "Personal Tax ID is required")
    @Column(name = "personal_tax_id", unique = true, nullable = false)
    private String personalTaxId;

    @NotBlank(message = "Postcode is required")
    @Column(name = "post_code", nullable = false)
    private String postCode;

    @NotBlank(message = "Emergency contact is required")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Emergency contact number is invalid")
    @Column(name = "emergency_contact", nullable = false)
    private String emergencyContact;

    @NotBlank(message = "Passport number is required")
    @Column(name = "passport_no", unique = true, nullable = false)
    private String passportNo;


    public String getFullName() {
        return user != null ? user.getFullname() : null;
    }

    public String getPhoneNumber() {
        return employee != null ? employee.getPhoneNumber() : null;
    }

    public String getEmailAddress() {
        return user != null ? user.getEmail() : null;
    }

}
