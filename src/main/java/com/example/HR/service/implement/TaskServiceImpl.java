package com.example.HR.service.implement;

import com.example.HR.converter.TaskConverter;
import com.example.HR.dto.task.TaskCommentDTO;
import com.example.HR.dto.task.TaskRequestDTO;
import com.example.HR.dto.task.TaskResponseDTO;
import com.example.HR.entity.employee.Employee;
import com.example.HR.entity.task.Task;
import com.example.HR.entity.task.TaskAssignment;
import com.example.HR.entity.task.TaskAttachment;
import com.example.HR.enums.TicketStatus;
import com.example.HR.exception.NotFoundException;
import com.example.HR.repository.EmployeeRepository;
import com.example.HR.repository.task.TaskAssignmentRepository;
import com.example.HR.repository.task.TaskAttachmentRepository;
import com.example.HR.repository.task.TaskCommentRepository;
import com.example.HR.repository.task.TaskRepository;
import com.example.HR.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public TaskResponseDTO create(TaskRequestDTO taskRequestDTO) {
        Task convertedTask = converter.toEntity(taskRequestDTO);
        Task savedTask = taskRepository.save(convertedTask);

        if(taskRequestDTO.getTeamMembers() != null && !taskRequestDTO.getTeamMembers().isEmpty()){
            assignEmployeesToTask(savedTask.getId(), taskRequestDTO.getTeamMembers());
        }

        return converter.toResponseDto(savedTask);

    }

    @Override
    public List<TaskResponseDTO> getAll() {
        return converter.toResponseDtoList(taskRepository.findAll());
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
    public TaskAttachment uploadAttachment(String ticketId, MultipartFile file) {
        return null;
    }

    @Override
    public TaskCommentDTO addComment(String taskId, String content) {
        return null;
    }

    @Override
    public void assignEmployeesToTask(Long taskId, List<Long> employeeIds) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found by id: " + taskId));
        for(Long employeeId : employeeIds){
            Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found by id: " + employeeId));

            Optional<TaskAssignment> existing = assignmentRepository
                    .findByTask_IdAndEmployee_Id(taskId,employeeId);

            if(existing.isEmpty()){
                TaskAssignment assignment = new TaskAssignment();
                assignment.setEmployee(employee);
                assignment.setTask(task);
                assignment.setAssignedTime(LocalDateTime.now());
                assignmentRepository.save(assignment);
            }
        }
    }
}
