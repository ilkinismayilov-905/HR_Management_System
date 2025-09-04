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
public class TaskAttachmentDTO {
    private Long id;
    private String fileName;
    private String originalFileName;
    private String contentType;
    private Long fileSize;
    private LocalDateTime uploadedDate;
}
