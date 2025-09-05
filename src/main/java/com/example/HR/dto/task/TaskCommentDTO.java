package com.example.HR.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TaskCommentDTO {
    private Long id;
    private String content;
    private String authorName;
    private String authorEmail;
    private LocalDateTime createdDate;
}
