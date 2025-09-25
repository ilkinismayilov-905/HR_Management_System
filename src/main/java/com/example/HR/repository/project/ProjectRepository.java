package com.example.HR.repository.project;

import com.example.HR.entity.project.Project;
import com.example.HR.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findByProjectAssignmentsEmployeeId(Long employeeId);
    List<Project> findByStatus(ProjectStatus status);
    List<Project> findByClient_ClientName(String clientName);

}
