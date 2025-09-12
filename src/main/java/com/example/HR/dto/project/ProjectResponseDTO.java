package com.example.HR.dto.project;

import com.example.HR.enums.ProjectPriority;
import com.example.HR.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProjectResponseDTO {
    private Long id;
    private String taskName;
    private ProjectPriority priority;
    private ProjectStatus status;
    private LocalDate timeLine;
    private List<ProjectAttachmentDTO> attachments;
    private List<ProjectEmployeeResponseDTO> people;
    private List<ProjectCommentDTO> comments;
}
