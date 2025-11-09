package com.example.HR.service;

import com.example.HR.dto.project.ProjectCommentDTO;
import com.example.HR.dto.project.ProjectRequestDTO;
import com.example.HR.dto.project.ProjectResponseDTO;
import com.example.HR.entity.project.ProjectAttachment;
import com.example.HR.enums.ProjectStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {
    ProjectResponseDTO create(ProjectRequestDTO projectRequestDTO);
    List<ProjectResponseDTO> getAll();
    ProjectResponseDTO getById(Long id);
    ProjectResponseDTO update(Long id,ProjectRequestDTO dto);
    List<ProjectResponseDTO> getByStatus(ProjectStatus status);
    void deleteById(Long id);
   ProjectAttachment uploadAttachment(Long projectId, MultipartFile file);
    ProjectCommentDTO addComment(Long taskId, String content);
    void assignEmployeesToTask(Long taskId, List<Long> employeeIds);
    List<ProjectCommentDTO> getAllComments();
}
