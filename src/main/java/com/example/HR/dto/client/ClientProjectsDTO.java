package com.example.HR.dto.client;

import com.example.HR.enums.ProjectPriority;
import com.example.HR.enums.ProjectStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClientProjectsDTO {
    private Long id;
    private String projectName;
    private ProjectPriority priority;
    private ProjectStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate timeLine;
}
