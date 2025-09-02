package com.example.HR.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketCommentDTO {
    private Long id;
    private String content;
    private String authorName;
    private String authorEmail;
    private LocalDateTime createdDate;
}
