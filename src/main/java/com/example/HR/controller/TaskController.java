package com.example.HR.controller;

import com.example.HR.config.TaskFileStorageProperties;
import com.example.HR.dto.task.TaskAttachmentDTO;
import com.example.HR.dto.task.TaskCommentDTO;
import com.example.HR.dto.task.TaskRequestDTO;
import com.example.HR.dto.task.TaskResponseDTO;
import com.example.HR.dto.ticket.TicketAttachmentDTO;
import com.example.HR.entity.task.TaskAttachment;
import com.example.HR.entity.ticket.TicketAttachment;
import com.example.HR.enums.TaskStatus;
import com.example.HR.service.TaskService;
import com.example.HR.service.implement.TaskImageStorageService;
import com.example.HR.validation.Create;
import com.example.HR.validation.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import org.springframework.web.server.ResponseStatusException;

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

    @Operation(
            summary = "Add a new task",
            description = "Creates a new task with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> createTask(@Validated(Create.class) @RequestBody TaskRequestDTO dto){

        log.info("REST request to create task");
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

    @Operation(
            summary = "Get all tasks",
            description = "Retrieves all tasks from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No tasks found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/get")
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {

        log.info("REST request to get all tasks");
        List<TaskResponseDTO> tasks = taskService.getAll();
        return ResponseEntity.ok(tasks);
    }

    @Operation(
            summary = "Get task by ID",
            description = "Retrieves task by ID from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No task found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getById(@PathVariable Long id){

        log.info("REST request to get task by ID");
        try {
            TaskResponseDTO task = taskService.getById(id);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",task);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(
            summary = "Delete task by ID",
            description = "Deletes task by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task was deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No task found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> deleteById(@PathVariable Long id){

        log.info("REST request to delete task by ID");
        try {
            TaskResponseDTO task = taskService.getById(id);
            taskService.deleteById(id);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",task);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

 @Operation(
            summary = "Update task",
            description = "Update an existing task by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update/{taskId}")
    public ResponseEntity<Map<String,Object>> updateTask(@Validated(Update.class)
                                                             @PathVariable Long taskId,
                                                         @RequestBody TaskRequestDTO dto){

     log.info("REST request to update task by ID");
        try {
            TaskResponseDTO task = taskService.update(taskId,dto);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",task);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(
            summary = "Get task by status",
            description = "Retrieves task by status from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No Task found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String,Object>> getByStatus(@PathVariable String status){

        log.info("REST request to get task by status");
        try {
            TaskStatus enumStatus;
            try {
                enumStatus= TaskStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Status: " + status);
            }

            List<TaskResponseDTO> task = taskService.getByStatus(enumStatus);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",task);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(
            summary = "Add a new comment",
            description = "Creates a new comment with the provided content")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{taskId}/comments")
    public ResponseEntity<Map<String,Object>> addComment(@PathVariable Long taskId,
                                                     @RequestBody Map<String, String> commentData) {

        log.info("REST request to add comment");
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

    @Operation(
            summary = "Get all comments",
            description = "Retrieves all comments from system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/comments")
    public ResponseEntity<Map<String,Object>> getAllComments(){
        log.info("REST request to get all comments");

        try {
            List<TaskCommentDTO> list = taskService.getAllComments();

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",list);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }
    }


    @Operation(
            summary = "Add a new task attachment",
            description = "Creates a new attachment with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task attachment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{taskId}/attachments")
    public ResponseEntity<Map<String, Object>> uploadFile(@PathVariable Long taskId,
                                                          @RequestParam("file") MultipartFile file) {

        log.info("REST request to add new attachment");
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

    @Operation(
            summary = "Download task attachments",
            description = "Downloads all task attachments from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task attachments downloaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No task attachments found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/attachments/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName){

        log.info("REST request to download attachment");
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +resource.getFilename() +"\"")
                .body(resource);
    }


    @Operation(
            summary = "Get task attachments",
            description = "Retrieves all task attachments from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task attachments retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No task attachments found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/attachments/view/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName){
        log.info("REST request to get attachments");
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}
