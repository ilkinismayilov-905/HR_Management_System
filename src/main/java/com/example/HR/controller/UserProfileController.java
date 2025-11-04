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
import org.springframework.http.HttpStatus;
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
@CrossOrigin(origins = "*")
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

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getAllProfiles() {
        List<UserProfileResponseDTO> data = userProfileService.findAll();
        Map<String, Object> response = new HashMap<>();

        try {

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
