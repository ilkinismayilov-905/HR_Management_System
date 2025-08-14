package com.example.HR.controller;

import com.example.HR.entity.EmailChat;
import com.example.HR.service.EmailChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emailChat")
@Validated
public class EmailChatController {

    private final EmailChatService chatService;

    @Autowired
    public EmailChatController(EmailChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/send")
    public ResponseEntity<EmailChat> sendEmail(@Valid @RequestBody EmailChat emailChat){
        EmailChat sentEmail = chatService.sendEmail(emailChat);
        return new ResponseEntity<>(sentEmail,HttpStatus.CREATED);
    }

    @GetMapping("/inbox/{recipient}")
    public ResponseEntity<Page<EmailChat>> getInbox(@PathVariable String recipient, Pageable pageable) {
        Page<EmailChat> inbox = chatService.getInbox(recipient, pageable);
        return ResponseEntity.ok(inbox);
    }

    @GetMapping("/sent/{sender}")
    public ResponseEntity<Page<EmailChat>> getSent(@PathVariable String sender, Pageable pageable) {
        Page<EmailChat> sent = chatService.getSent(sender, pageable);
        return ResponseEntity.ok(sent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailChat> getEmailById(@PathVariable Long id) {
        EmailChat email = chatService.getEmailById(id);
        return ResponseEntity.ok(email);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        chatService.markAsRead(id);
        return ResponseEntity.noContent().build();
    }
}
