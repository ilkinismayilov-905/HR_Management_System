package com.example.HR.repository.project;

import com.example.HR.entity.project.ProjectComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCommentRepository extends JpaRepository<ProjectComment,Long> {
}
