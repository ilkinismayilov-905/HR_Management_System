package com.example.HR.dto.user;

import com.example.HR.enums.City;
import com.example.HR.enums.Country;
import com.example.HR.enums.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInformationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false,unique = true)
    private String fullname;

    @Column(nullable = false,unique = true)
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Phone number is invalid")
    private String phoneNumber;

    @Column(nullable = false)
    @Email
    private String email;

    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private City city;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(nullable = false)
    private String postalCode;

}
