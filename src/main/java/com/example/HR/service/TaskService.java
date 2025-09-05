package com.example.HR.service;

import com.example.HR.dto.task.TaskCommentDTO;
import com.example.HR.dto.task.TaskRequestDTO;
import com.example.HR.dto.task.TaskResponseDTO;
import com.example.HR.dto.ticket.TicketCommentDTO;
import com.example.HR.dto.ticket.TicketRequestDTO;
import com.example.HR.dto.ticket.TicketResponseDTO;
import com.example.HR.entity.task.TaskAttachment;
import com.example.HR.entity.ticket.TicketAttachment;
import com.example.HR.enums.TicketStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskService {
    TaskResponseDTO create(TaskRequestDTO taskRequestDTO);
    List<TaskResponseDTO> getAll();
    TaskResponseDTO getById(Long id);
    TaskResponseDTO update(Long id,TaskRequestDTO dto);
    List<TaskResponseDTO> getByStatus(TicketStatus status);
    void deleteById(Long id);
    TaskAttachment uploadAttachment(String ticketId, MultipartFile file);
    TaskCommentDTO addComment(String taskId, String content);
    void assignEmployeesToTask(Long taskId, List<Long> employeeIds);
}
