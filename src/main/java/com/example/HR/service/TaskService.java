package com.example.HR.service;

import com.example.HR.dto.task.TaskCommentDTO;
import com.example.HR.dto.task.TaskRequestDTO;
import com.example.HR.dto.task.TaskResponseDTO;
import com.example.HR.entity.task.TaskAttachment;
import com.example.HR.enums.TaskStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskService {
    TaskResponseDTO create(TaskRequestDTO taskRequestDTO);
    List<TaskResponseDTO> getAll();
    TaskResponseDTO getById(Long id);
    TaskResponseDTO update(Long id,TaskRequestDTO dto);
    List<TaskResponseDTO> getByStatus(TaskStatus status);
    void deleteById(Long id);
    TaskAttachment uploadAttachment(Long ticketId, MultipartFile file);
    TaskCommentDTO addComment(Long taskId, String content);
    void assignEmployeesToTask(Long taskId, List<Long> employeeIds);
    List<TaskCommentDTO> getAllComments();
}
