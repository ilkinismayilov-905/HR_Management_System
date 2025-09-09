package com.example.HR.converter;

import com.example.HR.dto.employee.EmployeeResponseDTO;
import com.example.HR.dto.task.*;
import com.example.HR.entity.employee.Employee;
import com.example.HR.entity.task.Task;
import com.example.HR.entity.task.TaskAssignment;
import com.example.HR.entity.task.TaskAttachment;
import com.example.HR.entity.task.TaskComment;
import com.example.HR.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskConverter {

    private final EmployeeConverter converter;
    private final EmployeeRepository repository;

    public TaskResponseDTO toResponseDto(Task entity) {
        if (entity == null) return null;

        return TaskResponseDTO.builder()
                .id(entity.getId())
                .taskName(entity.getTaskName())
                .priority(entity.getPriority())
                .timeLine(entity.getTimeLine())

                // DÜZGÜN yol - birbaşa taskAssignments istifadə edin
                .people(entity.getTaskAssignments().stream()
                        .map(TaskAssignment::getEmployee)
                        .map(this::mapEmployeeToDto)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))

                // Attachments üçün - əgər lazy-dirsə, null check əlavə edin
                .attachments(mapAttachments(entity))

                // Comments üçün - əgər lazy-dirsə, null check əlavə edin
                .comments(mapComments(entity))

                .build();
    }

    // Helper metodlar - lazy loading-dən qorunmaq üçün
    private List<TaskAttachmentDTO> mapAttachments(Task entity) {
        try {
            return entity.getAttachments() != null
                    ? entity.getAttachments().stream()
                    .map(this::mapAttachmentToDto)
                    .collect(Collectors.toList())
                    : Collections.emptyList();
        } catch (LazyInitializationException e) {
            // Lazy loading uğursuz olarsa, boş list qaytarın
            return Collections.emptyList();
        }
    }

    private List<TaskCommentDTO> mapComments(Task entity) {
        try {
            return entity.getComments() != null
                    ? entity.getComments().stream()
                    .map(this::mapCommentToDTO)
                    .collect(Collectors.toList())
                    : Collections.emptyList();
        } catch (LazyInitializationException e) {
            // Lazy loading uğursuz olarsa, boş list qaytarın
            return Collections.emptyList();
        }
    }

    public List<TaskResponseDTO> toResponseDtoList(List<Task> list) {
        if (list == null) return Collections.emptyList();

        return list.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private TaskEmployeeResponseDTO mapEmployeeToDto(Employee employee) {
        if (employee == null) return null;

        return TaskEmployeeResponseDTO.builder()
                .id(employee.getId())
                .fullName(employee.getFullname().getFullname())
                .build();
    }

    private TaskAttachmentDTO mapAttachmentToDto(TaskAttachment attachment) {
        if (attachment == null) return null;

        return TaskAttachmentDTO.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .uploadedDate(attachment.getUploadedDate())
                .fileSize(attachment.getFileSize())
                .originalFileName(attachment.getOriginalFileName())
                .contentType(attachment.getContentType())
                .build();
    }

    public TaskCommentDTO mapCommentToDTO(TaskComment comment) {
        if (comment == null) return null;

        return TaskCommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate())
                .authorName(comment.getAuthorName() != null ? comment.getAuthorName() : null)
                .authorEmail(comment.getAuthorEmail() != null ? comment.getAuthorEmail() : null)
                .build();
    }

    public Task toEntity (TaskRequestDTO dto){

        Task task = Task.builder()
                .id(dto.getId())
                .taskName(dto.getTaskName())
                .status(dto.getStatus())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .timeLine(dto.getTimeLine())
                .build();

        if (dto.getTeamMembers() != null && !dto.getTeamMembers().isEmpty()) {

            Set<TaskAssignment> assignments = dto.getTeamMembers().stream()
                    .map(employeeId -> {
                        Employee employee = repository.findById(employeeId)
                                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));

                        TaskAssignment ta = new TaskAssignment();
                        ta.setEmployee(employee);
                        ta.setTask(task); // task reference burada vacibdir
                        return ta;
                    })
                    .collect(Collectors.toSet());

            task.setTaskAssignments(assignments);
        }

        return task;

    }

    public void update (TaskRequestDTO dto,Task entity){
        if(dto == null || entity == null) {return ;}

        entity.setId(dto.getId());
        entity.setTaskName(dto.getTaskName());
        entity.setDescription(dto.getDescription());
        entity.setPriority(dto.getPriority());
        entity.setStatus(dto.getStatus());
        entity.setTimeLine(dto.getTimeLine());

        if(dto.getTeamMembers() != null && !dto.getTeamMembers().isEmpty()){
            Set<TaskAssignment>assignments = dto.getTeamMembers().stream()
                    .map(employeeId -> {
                        Employee employee = repository.findById(employeeId)
                                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));

                        TaskAssignment taskAssignment = new TaskAssignment();
                        taskAssignment.setEmployee(employee);
                        taskAssignment.setTask(entity);

                        return taskAssignment;
                    })
                    .collect(Collectors.toSet());
            entity.setTaskAssignments(assignments);
        }
    }
}