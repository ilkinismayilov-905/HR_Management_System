package com.example.HR.dto.user;

import com.example.HR.enums.City;
import com.example.HR.enums.Country;
import com.example.HR.enums.State;
import com.example.HR.validation.Create;
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
public class UserProfileRequestDTO {
    @NotBlank(message = "Fullname cannot be blank",groups = Create.class)
    private String fullname;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank",groups = Create.class   )
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Phone number is invalid")
    private String phoneNumber;

    private String address;

    private City city;

    private State state;

    private Country country;

    private String postalCode;

}
