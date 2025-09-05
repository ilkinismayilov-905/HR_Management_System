package com.example.HR.converter;

import com.example.HR.dto.task.TaskAttachmentDTO;
import com.example.HR.dto.task.TaskCommentDTO;
import com.example.HR.dto.task.TaskRequestDTO;
import com.example.HR.dto.task.TaskResponseDTO;
import com.example.HR.entity.task.Task;
import com.example.HR.entity.task.TaskAttachment;
import com.example.HR.entity.task.TaskComment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskConverter {

    public TaskResponseDTO toResponseDto(Task entity){
        if(entity == null) return  null;

        return TaskResponseDTO.builder()
                .id(entity.getId())
                .taskName(entity.getTaskName())
                .people(entity.getAssignedEmployees().stream().toList())
                .priority(entity.getPriority())
                .timeLine(entity.getTimeLine())
                .attachments(entity.getAttachments().stream()
                        .map(this::mapAttachmentToDto)
                        .collect(Collectors.toList()))
                .comments(entity.getComments().stream()
                        .map(this::mapCommentToDto)
                        .collect(Collectors.toList()))

                .build();
    }

    public List<TaskResponseDTO> toResponseDtoList(List<Task> list){
        return list.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public Task toEntity(TaskRequestDTO dto){
        if (dto == null) return null;

        return Task.builder()
                .status(dto.getStatus())
                .taskName(dto.getTaskName())
                .timeLine(dto.getTimeLine())
                .priority(dto.getPriority())
                .id(dto.getId())
                .status(dto.getStatus())
                .description(dto.getDescription())
                .build();
    }

    public TaskCommentDTO mapCommentToDto(TaskComment comment){
        return TaskCommentDTO.builder()
                .id(comment.getId())
                .authorEmail(comment.getAuthorEmail())
                .authorName(comment.getAuthorName())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate())
                .build();

    }

    public TaskAttachmentDTO mapAttachmentToDto(TaskAttachment attachment){
        return TaskAttachmentDTO.builder()
                .id(attachment.getId())
                .contentType(attachment.getContentType())
                .originalFileName(attachment.getOriginalFileName())
                .fileName(attachment.getFileName())
                .uploadedDate(attachment.getUploadedDate())
                .fileSize(attachment.getFileSize())
                .build();
    }
}
