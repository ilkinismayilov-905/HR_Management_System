package com.example.HR.service.implement;

import com.example.HR.dto.project.ProjectResponseDTO;
import com.example.HR.entity.task.Task;
import com.example.HR.record.DashboardResponse;
import com.example.HR.repository.AttendanceRepository;
import com.example.HR.repository.client.ClientRepository;
import com.example.HR.repository.employee.EmployeeRepository;
import com.example.HR.repository.project.ProjectRepository;
import com.example.HR.repository.task.TaskRepository;
import com.example.HR.service.EmployeeService;
import com.example.HR.service.ProjectService;
import com.example.HR.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class DashboardService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;
    private final ProjectService projectService;

    public DashboardResponse getDashboard() {

        Long employees = employeeRepository.count();
        Long active = taskRepository.count();
        Long projects = projectRepository.count();
        Long clients = clientRepository.count();
        List<ProjectResponseDTO> list = projectService.getAll();


        return new DashboardResponse(
                employees,
                active,
                projects,
                clients,
                list
        );
    }
}
