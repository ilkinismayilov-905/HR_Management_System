package com.example.HR.repository;

import com.example.HR.entity.EmailChat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface EmailChatRepository extends JpaRepository<EmailChat,Long> {
    Page<EmailChat> findByRecipientOrderBySentAtDesc(String recipient, Pageable pageable);
    Page<EmailChat> findBySenderOrderBySentAtDesc(String sender, Pageable pageable);

}
