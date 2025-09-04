package com.example.HR.dto.task;

import com.example.HR.entity.employee.Employee;
import com.example.HR.enums.TaskPriority;
import com.example.HR.enums.TaskStatus;
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
public class TaskRequestDTO {

    private Long id;
    private LocalDate timeLine;
    private List<Employee> teamMembers;
    private TaskStatus status;
    private TaskPriority priority;
    private String description;
}
