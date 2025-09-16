package com.example.HR.service.implement;

import com.example.HR.converter.ProjectConverter;
import com.example.HR.dto.project.ProjectCommentDTO;
import com.example.HR.dto.project.ProjectRequestDTO;
import com.example.HR.dto.project.ProjectResponseDTO;
import com.example.HR.entity.User;
import com.example.HR.entity.employee.Employee;
import com.example.HR.entity.project.Project;
import com.example.HR.entity.project.ProjectAssignment;
import com.example.HR.entity.project.ProjectAttachment;
import com.example.HR.entity.project.ProjectComment;
import com.example.HR.enums.ProjectStatus;
import com.example.HR.exception.NotFoundException;
import com.example.HR.exception.ResourceNotFoundException;
import com.example.HR.repository.EmployeeRepository;
import com.example.HR.repository.project.ProjectAssignmentRepository;
import com.example.HR.repository.project.ProjectAttachmentRepository;
import com.example.HR.repository.project.ProjectCommentRepository;
import com.example.HR.repository.project.ProjectRepository;
import com.example.HR.service.ProjectService;
import com.example.HR.service.implement.fileStorage.ProjectFileStorageService;
import com.example.HR.service.implement.fileStorage.TaskImageStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectAssignmentRepository assignmentRepository;
    private final ProjectCommentRepository commentRepository;
    private final ProjectAttachmentRepository attachmentRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectConverter converter;
    private final ProjectFileStorageService storageService;

    @Override
    public ProjectResponseDTO create(ProjectRequestDTO projectRequestDTO) {
        Project convertedTask = converter.toEntity(projectRequestDTO);
        Project savedTask = projectRepository.save(convertedTask);

        if(projectRequestDTO.getTeamMembers() != null && !projectRequestDTO.getTeamMembers().isEmpty()){
            assignEmployeesToTask(savedTask.getId(), projectRequestDTO.getTeamMembers());
        }

        projectRepository.save(savedTask);

        return converter.toResponseDto(savedTask);
    }

    @Override
    public List<ProjectResponseDTO> getAll() {
        log.info("Fetching all projects");

        List<Project> task = projectRepository.findAll();
        log.info("projects fetched ");

        return converter.toResponseDtoList(task);
    }

    @Override
    public ProjectResponseDTO getById(Long id) {
        log.info("Get project by given id: {}", id);
        Project task = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found by id:" + id));

        log.info("Project fetched successfully from database.");

        return converter.toResponseDto(task);
    }

    @Override
    public ProjectResponseDTO update(Long id, ProjectRequestDTO dto) {
        log.info("Updating project: {}", id);
        Project task = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found by id:" + id));

        converter.update(dto,task);
        log.info("Project updated: {}", task.getProjectName());

        Project updatedTask = projectRepository.save(task);
        log.info("Project saved");

        return converter.toResponseDto(updatedTask);
    }

    @Override
    public List<ProjectResponseDTO> getByStatus(ProjectStatus status) {
        List<Project> task = projectRepository.findByStatus(status);

        log.info("Projects fetched by status");
        return converter.toResponseDtoList(task);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting project by id: {}", id);
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found by id:" + id));

        projectRepository.deleteById(id);
        log.info("Project deleted.");
    }

    @Override
    public ProjectAttachment uploadAttachment(Long projectId, MultipartFile file) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        ProjectAttachment attachment = storageService.storeFile(file,project);
        log.info("Uploaded attachment {} for project {}", file.getOriginalFilename(), projectId);

        return attachment;
    }

    @Override
    public ProjectCommentDTO addComment(Long taskId, String content) {
        Project projectComment = projectRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Project not found by id: " + taskId));

        User currentUser = getCurrentUser();

        ProjectComment comment = ProjectComment.builder()
                .content(content)
                .authorName(currentUser.getFullname())
                .authorEmail(currentUser.getUsername())
                .project(projectComment)
                .build();

        log.info("Comment added");

        comment = commentRepository.save(comment);
        log.info("Added comment to project: {}", taskId);

        return converter.mapCommentToDTO(comment);
    }

    @Override
    public void assignEmployeesToTask(Long taskId, List<Long> employeeIds) {
        Project task = projectRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Project not found by id: " + taskId));
        for(Long employeeId : employeeIds){
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new NotFoundException("Employee not found by id: " + employeeId));

            Optional<ProjectAssignment> existing = assignmentRepository
                    .findByProjectIdAndEmployeeId(taskId,employeeId);

            if(existing.isEmpty()){
                ProjectAssignment assignment = new ProjectAssignment();
                assignment.setEmployee(employee);
                assignment.setProject(task);
                assignment.setAssignedAt(LocalDateTime.now());
                assignmentRepository.save(assignment);
            }
        }
    }

    @Override
    public List<ProjectCommentDTO> getAllComments() {
        log.info("Get all ProjectComments");

        List<ProjectComment> commentDTOS = commentRepository.findAll();
        log.info("Comments fetched");

        return converter.mapCommentToDTOList(commentDTOS);
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
