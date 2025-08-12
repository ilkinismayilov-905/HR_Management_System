package com.example.HR.service.implement;

import com.example.HR.entity.EmailChat;
import com.example.HR.enums.EmailStatus;
import com.example.HR.exception.NotFoundException;
import com.example.HR.repository.EmailChatRepository;
import com.example.HR.service.EmailChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmailChatServiceImpl implements EmailChatService {

    private static final Logger log = LoggerFactory.getLogger(EmailChatService.class);

    private final EmailChatRepository chatRepository;

    @Autowired
    public EmailChatServiceImpl(EmailChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    @Transactional
    public EmailChat sendEmail(EmailChat email) {

        if (email.getSender() == null || email.getRecipient() == null) {
            throw new IllegalArgumentException("Sender və Recipient boş ola bilməz");
        }

        email.setSentAt(LocalDateTime.now());
        email.setRead(false);
        email.setStatus(EmailStatus.SENT);

        EmailChat savedEmail = chatRepository.save(email);
        log.info("Email göndərildi: id={}", savedEmail.getId());
        return savedEmail;
    }

    @Override
    @Transactional
    public Page<EmailChat> getInbox(String recipient, Pageable pageable) {
        return chatRepository.findByRecipientOrderBySentAtDesc(recipient,pageable);
    }

    @Override
    @Transactional
    public Page<EmailChat> getSent(String sender,Pageable pageable) {
        return chatRepository.findBySenderOrderBySentAtDesc(sender,pageable);
    }

    @Override
    @Transactional
    public EmailChat getEmailById(Long id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("EmailChat tapılmadı: id=" + id));
    }

    @Override
    @Transactional
    public void markAsRead(Long id) {
        EmailChat email = getEmailById(id);
        if (!email.isRead()) {
            email.setRead(true);
            chatRepository.save(email);
        }
    }
}

