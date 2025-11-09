package com.example.HR.repository.task;

import com.example.HR.entity.task.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment,Long> {

    @Query("SELECT ta FROM TaskAssignment ta WHERE ta.task.id = :taskId AND ta.employee.id = :employeeId")
    Optional<TaskAssignment> findByTaskIdAndEmployeeId(@Param("taskId") Long taskId, @Param("employeeId") Long employeeId);

    List<TaskAssignment> findByTaskId(Long taskId);
    List<TaskAssignment> findByEmployeeId(Long employeeId);

}
