package com.example.HR.entity.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "bank_information")
@AllArgsConstructor
@NoArgsConstructor
public class BankInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Bank name is required")
    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @NotBlank(message = "Account No is required")
    @Column(name = "account_no", unique = true, nullable = false)
    private Long accountNo;

    @NotBlank(message = "IFSC Code is required")
    @Column(name = "ifsc_code", unique = true, nullable = false)
    private String ifcsCode;

    @NotBlank(message = "Pan No is required")
    @Column(name = "pan", unique = true, nullable = false)
    private String panNo;

    @NotBlank(message = "Branch is required")
    @Column(name = "account_no",nullable = false)
    private Long branch;

    @NotBlank(message = "UPI ID is required")
    @Column(name = "upi_id", unique = true, nullable = false)
    private String upiId;

}
