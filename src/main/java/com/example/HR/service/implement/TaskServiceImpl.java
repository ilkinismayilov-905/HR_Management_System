package com.example.HR.service.implement;

import com.example.HR.converter.TaskConverter;
import com.example.HR.dto.task.TaskCommentDTO;
import com.example.HR.dto.task.TaskRequestDTO;
import com.example.HR.dto.task.TaskResponseDTO;
import com.example.HR.entity.User;
import com.example.HR.entity.employee.Employee;
import com.example.HR.entity.task.Task;
import com.example.HR.entity.task.TaskAssignment;
import com.example.HR.entity.task.TaskAttachment;
import com.example.HR.entity.task.TaskComment;
import com.example.HR.entity.ticket.Ticket;
import com.example.HR.entity.ticket.TicketAttachment;
import com.example.HR.enums.TicketStatus;
import com.example.HR.exception.NotFoundException;
import com.example.HR.exception.ResourceNotFoundException;
import com.example.HR.repository.EmployeeRepository;
import com.example.HR.repository.task.TaskAssignmentRepository;
import com.example.HR.repository.task.TaskAttachmentRepository;
import com.example.HR.repository.task.TaskCommentRepository;
import com.example.HR.repository.task.TaskRepository;
import com.example.HR.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskAssignmentRepository assignmentRepository;
    private final TaskCommentRepository commentRepository;
    private final TaskAttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final TaskConverter converter;
    private final TaskImageStorageService storageService;

    @Override
    public TaskResponseDTO create(TaskRequestDTO taskRequestDTO) {
        Task convertedTask = converter.toEntity(taskRequestDTO);
        Task savedTask = taskRepository.save(convertedTask);

        if(taskRequestDTO.getTeamMembers() != null && !taskRequestDTO.getTeamMembers().isEmpty()){
            assignEmployeesToTask(savedTask.getId(), taskRequestDTO.getTeamMembers());
        }

        taskRepository.save(savedTask);

        return converter.toResponseDto(savedTask);

    }

    @Override
    public List<TaskResponseDTO> getAll() {
        log.info("Fetching all tasks");

        List<Task> task = taskRepository.findAll();
        log.info("tasks fetched ");

        if(task.isEmpty()){
            throw new NotFoundException("task list is empty:");
        }

        List<TaskResponseDTO> list = converter.toResponseDtoList(task);

        return list;
    }

    @Override
    public TaskResponseDTO getById(Long id) {
        return null;
    }

    @Override
    public TaskResponseDTO update(Long id, TaskRequestDTO dto) {
        return null;
    }

    @Override
    public List<TaskResponseDTO> getByStatus(TicketStatus status) {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public TaskAttachment uploadAttachment(Long taskId, MultipartFile file) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        TaskAttachment attachment = storageService.storeFile(file,task);
        log.info("Uploaded attachment {} for ticket {}", file.getOriginalFilename(), taskId);

        return attachment;
    }

    @Override
    public TaskCommentDTO addComment(Long taskId, String content) {
        Task taskComment = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found by id: " + taskId));

        User currentUser = getCurrentUser();

         TaskComment comment = TaskComment.builder()
                .content(content)
                .authorName(currentUser.getFullname())
                .authorEmail(currentUser.getUsername())
                .task(taskComment)
                .build();

         log.info("Comment added");

         comment = commentRepository.save(comment);
        log.info("Added comment to task: {}", taskId);

        return converter.mapCommentToDTO(comment);

    }

    public void removeEmployeeFromTask(Long taskId, Long employeeId) {
        TaskAssignment assignment = assignmentRepository
                .findByTaskIdAndEmployeeId(taskId, employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        assignmentRepository.delete(assignment);
    }

    @Transactional(readOnly = true)
    public List<Task> getTasksByEmployee(Long employeeId) {
        return taskRepository.findTasksByEmployeeId(employeeId);
    }

    // Task-ın bütün employee-lərini əldə etmək
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByTask(Long taskId) {
        List<TaskAssignment> assignments = assignmentRepository.findByTaskId(taskId);
        return assignments.stream()
                .map(TaskAssignment::getEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public void assignEmployeesToTask(Long taskId, List<Long> employeeIds) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found by id: " + taskId));
        for(Long employeeId : employeeIds){
            Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found by id: " + employeeId));

            Optional<TaskAssignment> existing = assignmentRepository
                    .findByTaskIdAndEmployeeId(taskId,employeeId);

            if(existing.isEmpty()){
                TaskAssignment assignment = new TaskAssignment();
                assignment.setEmployee(employee);
                assignment.setTask(task);
                assignment.setAssignedAt(LocalDateTime.now());
                assignmentRepository.save(assignment);
            }
        }


    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
