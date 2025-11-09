package com.example.HR.dto.client;

import com.example.HR.enums.ClientStatus;
import com.example.HR.enums.JobTitle;
import com.example.HR.validation.Create;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientInformationDTO {

    @Schema(description = "Müştərinin unikal identifikatoru", example = "1")
    private Long id;

    @Schema(description = "Müştəri üçün sistemdə təyin olunmuş ID", example = "CL-1001")
    private String clientId;

    @Schema(description = "Şirkətin adı", example = "Tech Solutions LLC")
    private String companyName;

    private JobTitle jobTitle;

    private LocalDate startDate;

    private ClientStatus status;


}
