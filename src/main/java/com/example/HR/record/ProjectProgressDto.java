package com.example.HR.record;

public record ProjectProgressDto(Long projectId, String projectName, Long totalTasks, Long doneTasks, Double donePercent) {
}
