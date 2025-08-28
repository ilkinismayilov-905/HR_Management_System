package com.example.HR.dto.ticket;

import com.example.HR.entity.User;
import com.example.HR.enums.TicketStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketRequestDTO {

    @Schema(description = "Unique identifier of the ticket", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Subject or title of the ticket", example = "Fix login issue", required = true)
    @NotBlank(message = "Subject cannot be blank")
    @Size(max = 100, message = "Subject cannot exceed 100 characters")
    private String subject;

    @Schema(description = "User assigned to this ticket", required = true)
    @NotBlank(message = "Assigned to is required")
    private String assignedToFullName;

    @Schema(description = "Detailed description of the ticket", example = "Login fails with 500 error", required = true)
    @NotBlank(message = "Description cannot be blank")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Schema(description = "Current status of the ticket", example = "OPEN", required = true)
    @NotNull(message = "Status cannot be null")
    private TicketStatus status;

}
