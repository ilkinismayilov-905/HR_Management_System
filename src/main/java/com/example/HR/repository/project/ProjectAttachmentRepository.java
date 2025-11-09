package com.example.HR.repository.project;

import com.example.HR.entity.project.ProjectAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectAttachmentRepository extends JpaRepository<ProjectAttachment,Long> {
}
