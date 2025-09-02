package com.example.HR.controller;

import com.example.HR.dto.ticket.TicketAttachmentDTO;
import com.example.HR.dto.ticket.TicketCommentDTO;
import com.example.HR.dto.ticket.TicketRequestDTO;
import com.example.HR.dto.ticket.TicketResponseDTO;
import com.example.HR.entity.ticket.TicketAttachment;
import com.example.HR.enums.TicketStatus;
import com.example.HR.service.TicketService;
import com.example.HR.validation.Create;
import com.example.HR.validation.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ticket")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @Operation(
            summary = "Add a new ticket",
            description = "Creates a new ticket with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> add(@RequestBody @Validated(Create.class) TicketRequestDTO dto){
        try {
            TicketResponseDTO ticket = ticketService.create(dto);

            Map<String,Object> response = new HashMap<>();
            response.put("success:", true);
            response.put("data:",ticket);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){

            Map<String,Object> response = new HashMap<>();
            response.put("success:", false);
            response.put("message:","Failed to add ticket: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(
            summary = "Get all ticket",
            description = "Retrieves all tickets from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tickets retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No tickets found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAll")
    public ResponseEntity<Map<String,Object>> getAll(){
        try {
            List<TicketResponseDTO> list = ticketService.getAll();

            Map<String,Object> response = new HashMap<>();
            response.put("success:", true);
            response.put("data:",list);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){

            Map<String,Object> response = new HashMap<>();
            response.put("success:", false);
            response.put("message:","Failed to fetch tickets" + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(
            summary = "Update ticket",
            description = "Update an existing ticket by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long id,
                                                     @RequestBody @Validated(Update.class)TicketRequestDTO dto){
        log.info("REST request to update ticket with ID: {}", id);

        try {
            TicketResponseDTO ticket = ticketService.update(id,dto);

            Map<String,Object> response = new HashMap<>();
            response.put("success:",true);
            response.put("data:",ticket);
            response.put("message:","Ticket updated successfully");

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success:", false);
            response.put("message:","Failed to update ticket" + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(
            summary = "Get ticket by ID",
            description = "Retrieves ticket by ID from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No ticket found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getByID(@PathVariable Long id) {
        try {
            TicketResponseDTO list = ticketService.getById(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success:", true);
            response.put("data:", list);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {

            Map<String, Object> response = new HashMap<>();
            response.put("success:", false);
            response.put("message:", "Failed to fetch ticket: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @Operation(
            summary = "Get ticket by lastDays",
            description = "Retrieves ticket by lastDays from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No ticket found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getFromLastDays/{days}")
    public ResponseEntity<Map<String,Object>> getByLastDays(@PathVariable int days){
        log.info("Rest request to get tickets by last days");

        try {
            List<TicketResponseDTO> list = ticketService.getTicketsFromLastDays(days);

            Map<String,Object> response = new HashMap<>();
            response.put("success:",true);
            response.put("data:",list);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success:", false);
            response.put("message:", "Failed to fetch ticket: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @Operation(
            summary = "Get ticket by status",
            description = "Retrieves ticket by status from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No ticket found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{status}")
    public ResponseEntity<Map<String,Object>> getByStatus(@PathVariable TicketStatus status){
        log.info("Rest request to get ticket by status");

        try {
            List<TicketResponseDTO> dto = ticketService.getByStatus(status);

            Map<String,Object> response = new HashMap<>();
            response.put("success:",true);
            response.put("data:",dto);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {

            Map<String, Object> response = new HashMap<>();
            response.put("success:", false);
            response.put("message:", "Failed to fetch ticket: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }
    }


    @Operation(
            summary = "Delete ticket by ID",
            description = "Deletes ticket by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No ticket found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> deleteByID(@PathVariable Long id){

        log.info("Rest request to delete ticket by ID: {}", id);
        try {

            TicketResponseDTO dto = ticketService.getById(id);
            ticketService.deleteById(id);

            Map<String,Object> response = new HashMap<>();
            response.put("success:",true);
            response.put("message:","Ticket deleted by ID successfully");
            response.put("data:",dto);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success:",false);
            response.put("message:","Failed to delete ticket" + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/{ticketId}/attachments")
    public ResponseEntity<Map<String, Object>> uploadFile(@PathVariable String ticketId,
                                                          @RequestParam("file") MultipartFile file) {
        TicketAttachment attachment = ticketService.uploadAttachment(ticketId, file);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "File uploaded successfully");
        response.put("attachment", TicketAttachmentDTO.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .originalFileName(attachment.getOriginalFileName())
                .contentType(attachment.getContentType())
                .fileSize(attachment.getFileSize())
                .uploadedDate(attachment.getUploadedDate())
                .build());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{ticketId}/comments")
    public ResponseEntity<TicketCommentDTO> addComment(@PathVariable String ticketId,
                                                       @RequestBody Map<String, String> commentData) {
        TicketCommentDTO comment = ticketService.addComment(
                ticketId,
                commentData.get("content"),
                commentData.get("authorName"),
                commentData.get("authorEmail")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }


}
