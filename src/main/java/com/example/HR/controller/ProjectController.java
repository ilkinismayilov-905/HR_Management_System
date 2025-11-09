package com.example.HR.controller;

import com.example.HR.dto.project.ProjectAttachmentDTO;
import com.example.HR.dto.project.ProjectCommentDTO;
import com.example.HR.dto.project.ProjectRequestDTO;
import com.example.HR.dto.project.ProjectResponseDTO;
import com.example.HR.dto.task.TaskAttachmentDTO;
import com.example.HR.dto.task.TaskCommentDTO;
import com.example.HR.dto.task.TaskRequestDTO;
import com.example.HR.dto.task.TaskResponseDTO;
import com.example.HR.entity.project.ProjectAttachment;
import com.example.HR.entity.task.TaskAttachment;
import com.example.HR.enums.ProjectStatus;
import com.example.HR.enums.TaskStatus;
import com.example.HR.service.ProjectService;
import com.example.HR.service.TaskService;
import com.example.HR.service.implement.fileStorage.ProjectFileStorageService;
import com.example.HR.service.implement.fileStorage.TaskImageStorageService;
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
@RequestMapping("/project")
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectFileStorageService fileStorageService;

    @Operation(
            summary = "Add a new project",
            description = "Creates a new project with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> createTask(@Validated(Create.class) @RequestBody ProjectRequestDTO dto){

        log.info("REST request to create project");
        try {

            ProjectResponseDTO project = projectService.create(dto);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",project);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @Operation(
            summary = "Get all projects",
            description = "Retrieves all projects from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No projects found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/get")
    public ResponseEntity<List<ProjectResponseDTO>> getAllTasks() {

        log.info("REST request to get all tasks");
        List<ProjectResponseDTO> tasks = projectService.getAll();
        return ResponseEntity.ok(tasks);
    }

    @Operation(
            summary = "Get project by ID",
            description = "Retrieves project by ID from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No project found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getById(@PathVariable Long id){

        log.info("REST request to get project by ID");
        try {
            ProjectResponseDTO task = projectService.getById(id);

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
            summary = "Delete project by ID",
            description = "Deletes project by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project was deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No project found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> deleteById(@PathVariable Long id){

        log.info("REST request to delete project by ID");
        try {
            ProjectResponseDTO task = projectService.getById(id);
            projectService.deleteById(id);

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
            summary = "Update project",
            description = "Update an existing project by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update/{projectId}")
    public ResponseEntity<Map<String,Object>> updateTask(@Validated(Update.class)
                                                         @PathVariable Long projectId,
                                                         @RequestBody ProjectRequestDTO dto){

        log.info("REST request to update project by ID");
        try {
            ProjectResponseDTO task = projectService.update(projectId,dto);

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
            summary = "Get project by status",
            description = "Retrieves project by status from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No project found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String,Object>> getByStatus(@PathVariable String status){

        log.info("REST request to get project by status");
        try {
            ProjectStatus enumStatus;
            try {
                enumStatus= ProjectStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Status: " + status);
            }

            List<ProjectResponseDTO> task = projectService.getByStatus(enumStatus);

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
    @PostMapping("/{projectId}/comments")
    public ResponseEntity<Map<String,Object>> addComment(@PathVariable Long projectId,
                                                         @RequestBody Map<String, String> commentData) {

        log.info("REST request to add comment");
        try{
            ProjectCommentDTO comment = projectService.addComment(
                    projectId,
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
            List<ProjectCommentDTO> list = projectService.getAllComments();

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
            summary = "Add a new project attachment",
            description = "Creates a new attachment with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project attachment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{projectId}/attachments")
    public ResponseEntity<Map<String, Object>> uploadFile(@PathVariable Long projectId,
                                                          @RequestParam("file") MultipartFile file) {

        log.info("REST request to add new attachment");
        try{

            ProjectAttachment attachment = projectService.uploadAttachment(projectId, file);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "File uploaded successfully");
            response.put("attachment", ProjectAttachmentDTO.builder()
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
            summary = "Download project attachments",
            description = "Downloads all task attachments from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project attachments downloaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No project attachments found"),
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
            summary = "Get project attachments",
            description = "Retrieves all project attachments from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project attachments retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No project attachments found"),
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
