package com.example.HR.repository.task;

import com.example.HR.entity.task.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment,Long> {
}
