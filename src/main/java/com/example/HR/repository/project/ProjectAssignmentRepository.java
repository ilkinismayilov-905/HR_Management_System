package com.example.HR.repository.project;

import com.example.HR.entity.project.ProjectAssignment;
import com.example.HR.entity.task.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment,Long> {

    Optional<ProjectAssignment> findByProjectIdAndEmployeeId(Long projectId, Long employeeId);

    List<ProjectAssignment> findByProjectId(Long taskId);
}
