package com.example.HR.dto;

import com.example.HR.dto.client.ClientResponseDTO;
import com.example.HR.dto.employee.EmployeeResponseDTO;
import com.example.HR.dto.project.ProjectResponseDTO;
import com.example.HR.dto.task.TaskResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DashboardDTO {

    List<EmployeeResponseDTO> attendance;
    List<ProjectResponseDTO> totalProjects;
    List<ClientResponseDTO> totalClients;
    List<TaskResponseDTO> totalTasks;
    List<ProjectResponseDTO> timeLineProjects;
    List<TaskResponseDTO> timeLineTasks;

}
