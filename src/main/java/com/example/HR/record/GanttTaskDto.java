package com.example.HR.record;

import java.time.LocalDate;

public record GanttTaskDto (
        Long taskId,
        String taskName,
        LocalDate startDate,
        LocalDate endDate,
        Long employeeId,
        String employeeName,
        Long projectId,
        String projectName,
        String status
) {}
