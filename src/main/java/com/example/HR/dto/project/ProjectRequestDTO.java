package com.example.HR.dto.project;

import com.example.HR.enums.ProjectPriority;
import com.example.HR.enums.ProjectStatus;

import com.example.HR.validation.Create;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.http.parser.Upgrade;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProjectRequestDTO {


    @NotNull(groups = Upgrade.class, message = "Id is required for update")
    private Long id;

    // Create zamanı mütləqdir, Update zamanı optional
    @NotNull(groups = Create.class, message = "Timeline is required")
    private LocalDate timeLine;

    @NotBlank(groups = Create.class, message = "Task name is required")
    private String taskName;

    @NotEmpty(groups = Create.class, message = "Team members cannot be empty")
    private List<Long> teamMembers;

    @NotNull(groups = Create.class, message = "Status is required")
    private ProjectStatus status;

    @NotNull(groups = Create.class, message = "Priority is required")
    private ProjectPriority priority;

    @NotBlank(groups = Create.class, message = "Description is required")
    private String description;
}
