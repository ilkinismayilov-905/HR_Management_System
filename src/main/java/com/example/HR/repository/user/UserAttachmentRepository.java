package com.example.HR.repository.user;

import com.example.HR.entity.user.UserAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAttachmentRepository extends JpaRepository<UserAttachment,Long> {
}
