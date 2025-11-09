package com.example.HR.repository.client;

import com.example.HR.entity.client.ClientAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientAttachmentRepository extends JpaRepository<ClientAttachment,Long> {
}
