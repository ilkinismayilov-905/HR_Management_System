package com.example.HR.controller;

import com.example.HR.entity.employee.Employee;
import com.example.HR.entity.project.Project;
import com.example.HR.entity.task.Task;
import com.example.HR.record.DashboardResponse;
import com.example.HR.repository.task.TaskRepository;
import com.example.HR.service.implement.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final TaskRepository taskRepository;

    @GetMapping
    public DashboardResponse getDashboard() {
        return dashboardService.getDashboard();
    }
}
