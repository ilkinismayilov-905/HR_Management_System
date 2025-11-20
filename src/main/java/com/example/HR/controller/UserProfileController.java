package com.example.HR.controller;

import com.example.HR.dto.user.UserProfileResponseDTO;
import com.example.HR.dto.user.UserProfileRequestDTO;
import com.example.HR.entity.user.UserAttachment;
import com.example.HR.service.UserProfileService;
import com.example.HR.service.implement.fileStorage.UserImagesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/settings")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class UserProfileController {

    private UserProfileService  userProfileService;
    private final UserImagesService imageService;

    @PutMapping()
    public ResponseEntity<Map<String,Object>> updateUserProfile(@Valid @RequestBody UserProfileRequestDTO requestDTO,
                                                                Authentication authentication){
        log.info("Update user profile request {}", requestDTO.toString());
        Map<String,Object> response = new HashMap<>();
        String email = authentication.getName();

        try {
            UserProfileResponseDTO dto = userProfileService.updateProfile(requestDTO,email);
            response.put("success",true);
            response.put("data",dto);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalArgumentException e){
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        catch (Exception e) {

            response.put("success", false);
            response.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(
            summary = "Add a new user attachment",
            description = "Creates a new attachment with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User attachment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{id}/attachments")
    public ResponseEntity<Map<String, Object>> uploadFile(@PathVariable Long id,
                                                          @RequestParam("file") MultipartFile file) {
        UserAttachment attachment = userProfileService.uploadAttachment(id,file);
        Map<String, Object> response = new HashMap<>();

        try {

            response.put("success", true);
            response.put("message", "File uploaded successfully");
            response.put("attachment", UserAttachment.builder()
                    .id(attachment.getId())
                    .fileName(attachment.getFileName())
                    .originalFileName(attachment.getOriginalFileName())
                    .filePath(attachment.getFilePath())
                    .contentType(attachment.getContentType())
                    .fileSize(attachment.getFileSize())
                    .uploadedDate(attachment.getUploadedDate())
                    .build());

            return ResponseEntity.ok(response);
        }catch (IllegalArgumentException e){
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        catch (Exception e) {

            response.put("success", false);
            response.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
        Resource resource = imageService.loadFileAsResource(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +resource.getFilename() +"\"")
                .body(resource);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getAllProfiles() {

        Map<String, Object> response = new HashMap<>();

        try {
            List<UserProfileResponseDTO> data = userProfileService.findAll();
            response.put("success", true);
            response.put("data", data);

            return ResponseEntity.ok(response);
        }catch (IllegalArgumentException e){
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        catch (Exception e) {

            response.put("success", false);
            response.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getProfile(@PathVariable Long id,
                                                         Authentication authentication){

        log.info("REST request to get profile");
        Map<String,Object> response = new HashMap<>();
        String email = authentication.getName();

        try {
            UserProfileResponseDTO data = userProfileService.findById(id,email);
            response.put("success", true);
            response.put("data", data);

            return ResponseEntity.ok(response);
        }catch (IllegalArgumentException e){
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        catch (Exception e) {

            response.put("success", false);
            response.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
