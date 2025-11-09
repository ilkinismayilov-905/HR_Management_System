package com.example.HR.service;

import com.example.HR.entity.EmailChat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface EmailChatService {
    public EmailChat sendEmail(EmailChat email);
    public Page<EmailChat> getInbox(String recipient, Pageable pageable);
    public Page<EmailChat> getSent(String sender, Pageable pageable);
    public EmailChat getEmailById(Long id);
    public void markAsRead(Long id);
}
