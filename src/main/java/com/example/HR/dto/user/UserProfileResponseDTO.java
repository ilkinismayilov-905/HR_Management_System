package com.example.HR.dto.user;

import com.example.HR.dto.employee.EmployeeAttachmentDTO;
import com.example.HR.enums.City;
import com.example.HR.enums.Country;
import com.example.HR.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponseDTO {
    private Long userId;

    private String fullname;

    private String email;

    private String phoneNumber;

    private String address;

    private City city;

    private State state;

    private Country country;

    private String postalCode;

    private LocalDateTime updatedAt;

    private List<UserAttachmentDTO> attachments;
}
