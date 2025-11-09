package com.example.HR.dto.client;

import com.example.HR.enums.ClientStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Client məlumatlarının cavab DTO-su")
public class ClientResponseDTO {

    @Schema(description = "Müştərinin unikal identifikatoru", example = "1")
    private Long id;

    @Schema(description = "Müştəri üçün sistemdə təyin olunmuş ID", example = "CL-1001")
    private String clientId;

    @Schema(description = "Müştərinin adı", example = "John Doe")
    private String clientName;

    @Schema(description = "Şirkətin adı", example = "Tech Solutions LLC")
    private String companyName;

    @Schema(description = "Müştərinin email ünvanı", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Müştərinin telefon nömrəsi", example = "+994501112233")
    private String phoneNumber;

    @Schema(description = "Şirkət üzvlərinin siyahısı", example = "[\"Alice Smith\", \"Bob Johnson\"]")
    private List<String> companyMembers;

    @Schema(description = "Müştərinin statusu", example = "ACTIVE", allowableValues = {"ACTIVE", "INACTIVE", "SUSPENDED"})
    private ClientStatus status;
}
