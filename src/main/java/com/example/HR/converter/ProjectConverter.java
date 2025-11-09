package com.example.HR.converter;


import com.example.HR.dto.project.*;
import com.example.HR.entity.employee.Employee;
import com.example.HR.entity.project.Project;
import com.example.HR.entity.project.ProjectAssignment;
import com.example.HR.entity.project.ProjectAttachment;
import com.example.HR.entity.project.ProjectComment;
import com.example.HR.repository.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectConverter {

    private final EmployeeConverter converter;
    private final EmployeeRepository repository;

    public ProjectResponseDTO toResponseDto(Project entity) {
        if (entity == null) return null;

        return ProjectResponseDTO.builder()
                .id(entity.getId())
                .projectName(entity.getProjectName())
                .priority(entity.getPriority())
                .timeLine(entity.getTimeLine())

                // DÜZGÜN yol - birbaşa taskAssignments istifadə edin
                .people(entity.getProjectAssignments().stream()
                        .map(ProjectAssignment::getEmployee)
                        .map(this::mapEmployeeToDto)
                        .filter(Objects::nonNull)
                        .sorted(Comparator.comparing(e -> e.getId()))
                        .collect(Collectors.toList()))

                // Attachments üçün - əgər lazy-dirsə, null check əlavə edin
                .attachments(mapAttachments(entity))

                // Comments üçün - əgər lazy-dirsə, null check əlavə edin
//                .comments(mapComments(entity))
                .status(entity.getStatus())
                .build();
    }

    // Helper metodlar - lazy loading-dən qorunmaq üçün
    private List<ProjectAttachmentDTO> mapAttachments(Project entity) {
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

    public List<ProjectCommentDTO> mapComments(Project entity) {
        try {
            return entity.getComments() != null
                    ? entity.getComments().stream()
                    .map(this::mapCommentToDTO)
                    .collect(Collectors.toList())
                    : Collections.emptyList();
        } catch (LazyInitializationException e) {

            return Collections.emptyList();
        }
    }

    public List<ProjectResponseDTO> toResponseDtoList(List<Project> list) {
        if (list == null) return Collections.emptyList();

        return list.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private ProjectEmployeeResponseDTO mapEmployeeToDto(Employee employee) {
        if (employee == null) return null;

        return ProjectEmployeeResponseDTO.builder()
                .id(employee.getId())
                .fullName(employee.getFullname().getFullname())
                .build();
    }

    private ProjectAttachmentDTO mapAttachmentToDto(ProjectAttachment attachment) {
        if (attachment == null) return null;

        return ProjectAttachmentDTO.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .uploadedDate(attachment.getUploadedDate())
                .fileSize(attachment.getFileSize())
                .originalFileName(attachment.getOriginalFileName())
                .contentType(attachment.getContentType())
                .build();
    }

    public ProjectCommentDTO mapCommentToDTO(ProjectComment comment) {
        if (comment == null) return null;

        return ProjectCommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate())
                .authorName(comment.getAuthorName() != null ? comment.getAuthorName() : null)
                .authorEmail(comment.getAuthorEmail() != null ? comment.getAuthorEmail() : null)
                .build();
    }

    public List<ProjectCommentDTO> mapCommentToDTOList(List<ProjectComment> comments){
        return comments.stream()
                .map(this::mapCommentToDTO)
                .collect(Collectors.toList());
    }

    public Project toEntity (ProjectRequestDTO dto){

        Project task = Project.builder()
                .id(dto.getId())
                .projectName(dto.getTaskName())
                .status(dto.getStatus())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .timeLine(dto.getTimeLine())
                .build();

        if (dto.getTeamMembers() != null && !dto.getTeamMembers().isEmpty()) {

            Set<ProjectAssignment> assignments = dto.getTeamMembers().stream()
                    .map(employeeId -> {
                        Employee employee = repository.findById(employeeId)
                                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));

                        ProjectAssignment ta = new ProjectAssignment();
                        ta.setEmployee(employee);
                        ta.setProject(task); // task reference burada vacibdir
                        return ta;
                    })
                    .collect(Collectors.toSet());

            task.setProjectAssignments(assignments);
        }

        return task;

    }

    public void update (ProjectRequestDTO dto,Project entity){
        if(dto == null || entity == null) {return ;}

//        entity.setId(dto.getId());
        if (dto.getTaskName() != null) {
            entity.setProjectName(dto.getTaskName());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getPriority() != null) {
            entity.setPriority(dto.getPriority());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getTimeLine() != null) {
            entity.setTimeLine(dto.getTimeLine());
        }

        if(dto.getTeamMembers() != null && !dto.getTeamMembers().isEmpty()){
            Set<ProjectAssignment>assignments = dto.getTeamMembers().stream()
                    .map(employeeId -> {

                        Employee employee = repository.findById(employeeId)
                                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));

                        ProjectAssignment taskAssignment = new ProjectAssignment();
                        taskAssignment.setEmployee(employee);
                        taskAssignment.setProject(entity);

                        log.info("assignments size = {}", entity.getProjectAssignments().size());
                        entity.getProjectAssignments().forEach(a ->
                                log.info("assignment -> id: {}, employeeId: {}, employeeName: {}",
                                        a.getId(), a.getEmployee() == null ? null : a.getEmployee().getId(),
                                        a.getEmployee() == null ? null : a.getEmployee().getFullname()));

                        return taskAssignment;
                    })
                    .collect(Collectors.toSet());
            entity.getProjectAssignments().clear();
            entity.getProjectAssignments().addAll(assignments);
        }
    }
}
