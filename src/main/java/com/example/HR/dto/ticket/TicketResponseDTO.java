package com.example.HR.dto.ticket;

import com.example.HR.dto.user.UserResponseDTO;
import com.example.HR.enums.TicketStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponseDTO {

    @Schema(description = "Unique identifier of the ticket", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Unique ticket ID", example = "#TC-001")
    private String ticketID;

    @Schema(description = "Subject of the ticket", example = "Fix login issue")
    private String subject;

    @Schema(description = "Assigned user info")
    private String assignedTo;

    @Schema(description = "Ticket description", example = "Login fails with 500 error")
    private String description;

//    private String assignedToFullName;

    @Schema(description = "Assigned user email")
    private String email;

    @Schema(description = "Ticket status", example = "OPEN")
    private TicketStatus status;
}
