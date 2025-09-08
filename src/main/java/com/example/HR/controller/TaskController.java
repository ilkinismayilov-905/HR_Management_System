package com.example.HR.controller;

import com.example.HR.config.TaskFileStorageProperties;
import com.example.HR.dto.task.TaskAttachmentDTO;
import com.example.HR.dto.task.TaskCommentDTO;
import com.example.HR.dto.task.TaskRequestDTO;
import com.example.HR.dto.task.TaskResponseDTO;
import com.example.HR.dto.ticket.TicketAttachmentDTO;
import com.example.HR.entity.task.TaskAttachment;
import com.example.HR.entity.ticket.TicketAttachment;
import com.example.HR.service.TaskService;
import com.example.HR.service.implement.TaskImageStorageService;
import com.example.HR.validation.Create;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskImageStorageService fileStorageService;

    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> createTask(@Validated(Create.class) @RequestBody TaskRequestDTO dto){

        try {

            TaskResponseDTO task = taskService.create(dto);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",task);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping("/get")
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.getAll();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/{taskId}/comments")
    public ResponseEntity<Map<String,Object>> addComment(@PathVariable Long taskId,
                                                     @RequestBody Map<String, String> commentData) {
        try{
            TaskCommentDTO comment = taskService.addComment(
                    taskId,
                    commentData.get("content")
            );

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PostMapping("/{taskId}/attachments")
    public ResponseEntity<Map<String, Object>> uploadFile(@PathVariable Long taskId,
                                                          @RequestParam("file") MultipartFile file) {
        try{

            TaskAttachment attachment = taskService.uploadAttachment(taskId, file);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "File uploaded successfully");
            response.put("attachment", TaskAttachmentDTO.builder()
                    .id(attachment.getId())
                    .fileName(attachment.getFileName())
                    .originalFileName(attachment.getOriginalFileName())
                    .contentType(attachment.getContentType())
                    .fileSize(attachment.getFileSize())
                    .uploadedDate(attachment.getUploadedDate())
                    .build());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


    }

    @GetMapping("/attachments/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName){
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +resource.getFilename() +"\"")
                .body(resource);
    }

    @GetMapping("/attachments/view/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName){
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
