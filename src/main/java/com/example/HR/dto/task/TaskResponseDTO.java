package com.example.HR.dto.task;

import com.example.HR.dto.employee.EmployeeResponseDTO;
import com.example.HR.dto.ticket.TicketAttachmentDTO;
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
public class TaskResponseDTO {

    private Long id;
    private String taskName;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDate timeLine;
    private List<TaskAttachmentDTO> attachments;
    private List<TaskEmployeeResponseDTO> people;
    private List<TaskCommentDTO> comments;
}
