package com.example.HR.record;

import com.example.HR.dto.project.ProjectResponseDTO;

import java.util.List;

public record DashboardResponse(
        Long totalEmployees,
        Long activeTasks,
        Long totalProjects,
        Long totalClient,
        List<ProjectResponseDTO> recentProjects)
{}
