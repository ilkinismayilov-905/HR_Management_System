package com.example.HR.controller;

import com.example.HR.entity.EmailChat;
import com.example.HR.service.EmailChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/emailChat")
@Validated
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EmailChatController {

    private final EmailChatService chatService;

    @Operation(
            summary = "Send an email",
            description = "Send an email message with the provided details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "500", description = "Internal server error while sending email")
    })
    @PostMapping("/send")
    public ResponseEntity<Map<String,Object>> sendEmail(@Valid @RequestBody EmailChat emailChat){

        log.info("REST request to send email");

        try {
            EmailChat sentEmail = chatService.sendEmail(emailChat);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",sentEmail);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }


    @Operation(
            summary = "Get inbox messages",
            description = "Retrieve all inbox emails for a given recipient with pagination support"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inbox retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No emails found for the given recipient"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/inbox/{recipient}")
    public ResponseEntity<Map<String,Object>> getInbox(@PathVariable String recipient, Pageable pageable) {

        log.info("REST request to get inbox");

        try {
            Page<EmailChat> inbox = chatService.getInbox(recipient, pageable);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",inbox);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }


    @Operation(
            summary = "Get sent emails",
            description = "Retrieve all sent emails for a given sender with pagination support"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sent emails retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No sent emails found for the given sender"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/sent/{sender}")
    public ResponseEntity<Map<String,Object>> getSent(@PathVariable String sender, Pageable pageable) {

        log.info("REST request to get sent emails");

        try {
            Page<EmailChat> sent = chatService.getSent(sender, pageable);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",sent);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @Operation(
            summary = "Get email by ID",
            description = "Retrieve a specific email by its unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Email not found with the given ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getEmailById(@PathVariable Long id) {

        log.info("REST request to get mail by ID");

        try {
            EmailChat email = chatService.getEmailById(id);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",email);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @Operation(
            summary = "Mark email as read",
            description = "Update the status of a specific email to 'read' by its unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email marked as read successfully"),
            @ApiResponse(responseCode = "404", description = "Email not found with the given ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}/read")
    public ResponseEntity<Map<String,Object>> markAsRead(@PathVariable Long id) {

        log.info("REST request to mark email as Read");

        try {
            chatService.markAsRead(id);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }
}
