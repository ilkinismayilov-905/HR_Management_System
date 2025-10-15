package com.example.HR.controller;

import com.example.HR.dto.client.ClientInformationDTO;
import com.example.HR.dto.client.ClientProjectsDTO;
import com.example.HR.dto.client.ClientRequestDTO;
import com.example.HR.dto.client.ClientResponseDTO;
import com.example.HR.dto.employee.EmployeeAttachmentDTO;
import com.example.HR.entity.client.ClientAttachment;
import com.example.HR.enums.ClientStatus;
import com.example.HR.service.ClientService;
import com.example.HR.service.implement.fileStorage.ClientImagesService;
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
@RequestMapping("/client")
@Slf4j
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;
    private final ClientImagesService fileStorageService;

    @Operation(
            summary = "Add a new client",
            description = "Creates a new client with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> addClient(@Validated(Create.class) @RequestBody ClientRequestDTO dto){
       log.info("REST request to add new client: {}",dto.getFullname());

        try {
            ClientResponseDTO client = service.save(dto);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",client);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(
            summary = "Get all clients",
            description = "Retrieves all clients from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clients retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No clients found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAll")
    public ResponseEntity<Map<String,Object>> getAll(){
        log.info("REST request to show all clients");

        try {
            List<ClientResponseDTO> list = service.getAll();

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",list);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @Operation(
            summary = "Get client by ID",
            description = "Retrieves client by ID from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No client found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getById(@PathVariable Long id){
        log.info("REST request to get client by ID: {}", id);

        try {
            ClientResponseDTO client = service.getById(id);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",client);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(
            summary = "Get client by status",
            description = "Retrieves client by status from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No client found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String,Object>> getByStatus(@PathVariable String status){
        log.info("REST request to get client by ststus: {}", status);

        try {
            ClientStatus enumStatus;
            try {
                enumStatus= ClientStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status: " + status);
            }
            List<ClientResponseDTO> client = service.getByStatus(enumStatus);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",client);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @Operation(
            summary = "Update client",
            description = "Update an existing client by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String,Object>> update(@Validated(Update.class) @RequestBody ClientRequestDTO dto,
                                                     @PathVariable Long id){
        log.info("REST request to update client by ID: {}", id);

        try {
            ClientResponseDTO client = service.update(id, dto);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",client);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(
            summary = "Delete client by ID",
            description = "Deletes client by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client was deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No client found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> deleteById(@PathVariable Long id){
        log.info("REST request to delete client by ID: {}", id);

        try {
            ClientResponseDTO client = service.getById(id);

            service.deleteById(id);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",client);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(
            summary = "Add a new client attachment",
            description = "Creates a new attachment with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client attachment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{clientId}/attachments")
    public ResponseEntity<Map<String, Object>> uploadFile(@PathVariable String clientId,
                                                          @RequestParam("file") MultipartFile file) {
        log.info("REST request to upload attachment to client by ID: {}", clientId);

        try {

            ClientAttachment attachment = service.uploadAttachment(clientId, file);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "File uploaded successfully");
            response.put("attachment", EmployeeAttachmentDTO.builder()
                    .id(attachment.getId())
                    .fileName(attachment.getFileName())
                    .originalFileName(attachment.getOriginalFileName())
                    .contentType(attachment.getContentType())
                    .fileSize(attachment.getFileSize())
                    .uploadedDate(attachment.getUploadedDate())
                    .build());

            return ResponseEntity.ok(response);
        }catch (Exception e) {
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(
            summary = "Download client attachments",
            description = "Downloads all client attachments from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client attachments downloaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No client attachments found"),
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
            summary = "Get client attachments",
            description = "Retrieves all client attachments from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client attachments retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No client attachments found"),
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

    @Operation(
            summary = "Get client information",
            description = "Retrieves all client information from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client information retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No client information found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/clientInfo/{clientId}")
    public ResponseEntity<Map<String,Object>> getClientInfo(@PathVariable String clientId){
        log.info("Rest request to get clienInfo");

        try {
            ClientInformationDTO clientInfo = service.getClientInfo(clientId);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",clientInfo);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(
            summary = "Get client's projects",
            description = "Retrieves client's all projects from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client projects retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No client projects found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/projects/{username}")
    public ResponseEntity<Map<String ,Object>> getProjects(@PathVariable String username){
        log.info("REST request to get client's projects");

        try {
            List<ClientProjectsDTO> list = service.getCurrentProjects(username);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",list);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {
            Map<String,Object> response = new HashMap<>();
            response.put("s",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


}
